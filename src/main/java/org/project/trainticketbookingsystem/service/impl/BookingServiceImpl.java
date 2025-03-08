package org.project.trainticketbookingsystem.service.impl;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.trainticketbookingsystem.config.UserDetailsImpl;
import org.project.trainticketbookingsystem.dto.BookingDto;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDto;
import org.project.trainticketbookingsystem.entity.BookingEntity;
import org.project.trainticketbookingsystem.entity.SeatEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.entity.UserEntity;
import org.project.trainticketbookingsystem.mapper.BookingMapper;
import org.project.trainticketbookingsystem.mapper.RouteMapper;
import org.project.trainticketbookingsystem.mapper.SeatMapper;
import org.project.trainticketbookingsystem.mapper.StationMapper;
import org.project.trainticketbookingsystem.repository.BookingRepository;
import org.project.trainticketbookingsystem.service.BookingSeatService;
import org.project.trainticketbookingsystem.service.BookingService;
import org.project.trainticketbookingsystem.service.RouteStationTimeService;
import org.project.trainticketbookingsystem.service.TrainService;
import org.project.trainticketbookingsystem.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final SeatMapper seatMapper;
    private final TrainService trainService;
    private final RouteStationTimeService routeStationTimeService;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final BookingSeatService bookingSeatService;
    private final RouteMapper routeMapper;
    private final StationMapper stationMapper;

    @Override
    public BookingDto bookTicket(BookingDto bookingDTO, UserDetails user) {
        Long userId = ((UserDetailsImpl) user).getId();

        Map<String, RouteStationTimeDto> stations = routeStationTimeService.findByRouteIdAndStationId(bookingDTO.getRouteId(), bookingDTO.getDepartureStation().getId(), bookingDTO.getArrivalStation().getId());

        if (stations.get("departureStation").getStationDTO().getId().equals(bookingDTO.getArrivalStation().getId())) {
            RouteStationTimeDto temp = stations.get("departureStation");
            stations.put("departureStation", stations.get("arrivalStation"));
            stations.put("arrivalStation", temp);
        }

        List<SeatEntity> seatEntities = seatMapper.toSeatEntity(bookingSeatService.getSeatBooking(bookingDTO, stations));

        TrainEntity train = trainService.getTrainEntityById(bookingDTO.getTrainId());
        UserEntity userEntity = userService.findById(userId);

        BookingEntity booking = BookingEntity.builder()
                .seats(seatEntities)
                .train(train)
                .route(routeMapper.toRouteEntity(stations.get("departureStation").getRouteDTO()))
                .departureStation(stationMapper.toStationEntity(stations.get("departureStation").getStationDTO()))
                .arrivalStation(stationMapper.toStationEntity(stations.get("arrivalStation").getStationDTO()))
                .arrivalDate(bookingDTO.getTravelDate())
                .user(userEntity)
                .build();

        return bookingMapper.toBookingDTO(bookingRepository.save(booking));
    }
}
