package org.project.trainticketbookingsystem.service.impl;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.trainticketbookingsystem.dto.BookingDto;
import org.project.trainticketbookingsystem.entity.BookingEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;
import org.project.trainticketbookingsystem.entity.SeatEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.entity.UserEntity;
import org.project.trainticketbookingsystem.exceptions.SeatException;
import org.project.trainticketbookingsystem.mapper.BookingMapper;
import org.project.trainticketbookingsystem.repository.BookingRepository;
import org.project.trainticketbookingsystem.service.BookingService;
import org.project.trainticketbookingsystem.service.RouteStationTimeService;
import org.project.trainticketbookingsystem.service.SeatService;
import org.project.trainticketbookingsystem.service.TrainService;
import org.project.trainticketbookingsystem.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final SeatService seatService;
    private final TrainService trainService;
    private final RouteStationTimeService routeStationTimeService;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;

    @Override
    public BookingDto bookTicket(BookingDto bookingDTO, UserDetails user) {
        //расширить user details с id
        Long userId = ((UserEntity) user).getId();

        List<RouteStationTimeEntity> stations = routeStationTimeService.findByRouteIdAndStationId(bookingDTO.getRouteId(), bookingDTO.getDepartureStation().getId(), bookingDTO.getArrivalStation().getId());
        RouteStationTimeEntity departureStation;
        RouteStationTimeEntity arrivalStation;

        if (stations.get(0).getStation().getId().equals(bookingDTO.getDepartureStation().getId())) {
            departureStation = stations.get(0);
            arrivalStation = stations.get(1);
        } else {
            departureStation = stations.get(1);
            arrivalStation = stations.get(2);
        }

        List<SeatEntity> seatEntity = seatService.getSeatEntityFromBooking(bookingDTO);
        List<Long> seatIds = seatEntity.stream()
                .map(SeatEntity::getId)
                .collect(Collectors.toList());

        List<BookingEntity> allBookings = bookingRepository.findByTrainIdAndRouteIdAndArrivalDate(
                bookingDTO.getTrainId(),
                bookingDTO.getRouteId(),
                bookingDTO.getTravelDate()
        );

        hasConflicts(allBookings, departureStation, arrivalStation, bookingDTO.getRouteId(), seatIds);

        TrainEntity train = trainService.getTrainEntityById(bookingDTO.getTrainId());
        UserEntity userEntity = userService.findById(userId);

        BookingEntity booking = BookingEntity.builder()
                .seats(seatEntity)
                .train(train)
                .route(departureStation.getRoute())
                .departureStation(departureStation.getStation())
                .arrivalStation(arrivalStation.getStation())
                .arrivalDate(bookingDTO.getTravelDate())
                .user(userEntity)
                .build();

        return bookingMapper.toBookingDTO(bookingRepository.save(booking));
    }

    private boolean hasConflicts(List<BookingEntity> allBookings,
                                 RouteStationTimeEntity departureStation,
                                 RouteStationTimeEntity arrivalStation,
                                 Long routeID,
                                 List<Long> seatIds) {

        int departureStopOrder = departureStation.getStopOrder();
        int arrivalStopOrder = arrivalStation.getStopOrder();

        boolean hasConflict = allBookings.stream()
                .filter(booking -> booking.getSeats().
                        stream().anyMatch(seat -> seatIds.contains(seat.getId())))
                .anyMatch(booking -> {
                    List<RouteStationTimeEntity> bookedStations = routeStationTimeService.findByRouteIdAndStationId(
                            routeID, booking.getDepartureStation().getId(), booking.getArrivalStation().getId()
                    );

                    if (bookedStations.size() < 2) {
                        return false;
                    }

                    int bookedDepartureOrder = bookedStations.get(0).getStopOrder();
                    int bookedArrivalOrder = bookedStations.get(1).getStopOrder();

                    return (departureStopOrder >= bookedDepartureOrder && departureStopOrder < bookedArrivalOrder) ||
                            (arrivalStopOrder > bookedDepartureOrder && arrivalStopOrder <= bookedArrivalOrder);
                });

        if (hasConflict) {
            throw new SeatException("The seat is occupied");
        }
        return false;
    }
}
