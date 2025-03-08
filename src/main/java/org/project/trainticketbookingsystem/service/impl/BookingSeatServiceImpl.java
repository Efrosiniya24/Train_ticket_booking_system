package org.project.trainticketbookingsystem.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.BookingDto;
import org.project.trainticketbookingsystem.dto.CoachDto;
import org.project.trainticketbookingsystem.dto.CoachSeatInfo;
import org.project.trainticketbookingsystem.dto.GetSeatStatusDto;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDto;
import org.project.trainticketbookingsystem.dto.SeatDto;
import org.project.trainticketbookingsystem.dto.SeatInfo;
import org.project.trainticketbookingsystem.dto.SeatStatusDtoResponse;
import org.project.trainticketbookingsystem.entity.BookingEntity;
import org.project.trainticketbookingsystem.entity.SeatEntity;
import org.project.trainticketbookingsystem.exceptions.SeatException;
import org.project.trainticketbookingsystem.mapper.SeatMapper;
import org.project.trainticketbookingsystem.repository.BookingRepository;
import org.project.trainticketbookingsystem.repository.SeatRepository;
import org.project.trainticketbookingsystem.service.BookingSeatService;
import org.project.trainticketbookingsystem.service.CoachService;
import org.project.trainticketbookingsystem.service.RouteStationTimeService;
import org.project.trainticketbookingsystem.service.SeatService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingSeatServiceImpl implements BookingSeatService {
    private final BookingRepository bookingRepository;
    private final SeatService seatService;
    private final RouteStationTimeService routeStationTimeService;
    private final CoachService coachService;
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    @Override
    public List<SeatDto> getSeatBooking(BookingDto bookingDto, Map<String, RouteStationTimeDto> stations) {
        List<SeatEntity> seatEntity = bookingDto.getSeatsList().stream()
                .map(currentSeat -> seatService.getSeatById(currentSeat.getId()))
                .collect(Collectors.toList());

        List<Long> seatIds = seatEntity.stream()
                .map(SeatEntity::getId)
                .collect(Collectors.toList());

        List<BookingEntity> allBookings = bookingRepository.findByTrainIdAndRouteIdAndArrivalDate(
                bookingDto.getTrainId(),
                bookingDto.getRouteId(),
                bookingDto.getTravelDate()
        );

        hasConflicts(allBookings, stations.get("departureStation"), stations.get("arrivalStation"), bookingDto.getRouteId(), seatIds);
        return seatMapper.toSeatDTO(seatEntity);
    }

    private boolean hasConflicts(List<BookingEntity> allBookings,
                                 RouteStationTimeDto departureStation,
                                 RouteStationTimeDto arrivalStation,
                                 Long routeID,
                                 List<Long> seatIds) {

        int departureStopOrder = departureStation.getStopOrder();
        int arrivalStopOrder = arrivalStation.getStopOrder();

        boolean hasConflict = allBookings.stream()
                .filter(booking -> booking.getSeats().
                        stream().anyMatch(seat -> seatIds.contains(seat.getId())))
                .anyMatch(booking -> {
                    Map<String, RouteStationTimeDto> bookedStations = routeStationTimeService.findByRouteIdAndStationId(
                            routeID, booking.getDepartureStation().getId(), booking.getArrivalStation().getId()
                    );

                    if (bookedStations.size() < 2) {
                        return false;
                    }

                    int bookedDepartureOrder = bookedStations.get("departureStation").getStopOrder();
                    int bookedArrivalOrder = bookedStations.get("arrivalStation").getStopOrder();

                    return (departureStopOrder >= bookedDepartureOrder && departureStopOrder < bookedArrivalOrder) ||
                            (arrivalStopOrder > bookedDepartureOrder && arrivalStopOrder <= bookedArrivalOrder);
                });

        if (hasConflict) {
            throw new SeatException("The seat is occupied");
        }
        return false;
    }

    @Override
    public SeatStatusDtoResponse getSeatsWithStatusForSegment(GetSeatStatusDto statusDto) {
        List<CoachDto> coachDtos = coachService.getCoachList(statusDto.getTrainId());
        List<Long> coachIds = coachDtos.stream().map(CoachDto::getId).collect(Collectors.toList());
        List<SeatEntity> seats = seatRepository.findByCoachIdIn(coachIds);

        List<BookingEntity> bookingEntities = bookingRepository.findAll().stream()
                .filter(booking -> booking.getRoute().getId().equals(statusDto.getRouteId()) &&
                        booking.getDepartureStation().getId().equals(statusDto.getDepartureStationId()) &&
                        booking.getArrivalStation().getId().equals(statusDto.getArrivalStationId()))
                .collect(Collectors.toList());

        List<Long> bookedSeatIds = bookingEntities.stream()
                .flatMap(booking -> booking.getSeats().stream().map(SeatEntity::getId))
                .collect(Collectors.toList());

        Map<Long, List<SeatInfo>> coachSeatMap = seats.stream()
                .collect(Collectors.groupingBy(
                        seat -> seat.getCoach().getId(),
                        Collectors.mapping(seat -> SeatInfo.builder()
                                .seatId(seat.getId())
                                .booked(bookedSeatIds.contains(seat.getId()))
                                .build(), Collectors.toList())
                ));

        Map<Long, CoachSeatInfo> coachDtosMap = coachSeatMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<SeatInfo> seatsInfo = entry.getValue();
                            long freeSeatsCount = seatsInfo.stream().filter(seat -> !seat.isBooked()).count();

                            return CoachSeatInfo.builder()
                                    .coachId(entry.getKey())
                                    .seats(seatsInfo)
                                    .freeSeats((int) freeSeatsCount)
                                    .build();
                        }
                ));

        int totalFreeSeats = coachDtosMap.values().stream().mapToInt(CoachSeatInfo::getFreeSeats).sum();

        return SeatStatusDtoResponse.builder()
                .coachDtos(coachDtosMap)
                .freeSeats(totalFreeSeats)
                .build();
    }
}
