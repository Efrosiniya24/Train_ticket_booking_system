package org.project.trainticketbookingsystem.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.BookingDTO;
import org.project.trainticketbookingsystem.entity.*;
import org.project.trainticketbookingsystem.mapper.BookingMapper;
import org.project.trainticketbookingsystem.repository.*;
import org.project.trainticketbookingsystem.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final SeatService seatService;
    private final TrainService trainService;
    private final RouteStationTimeService routeStationTimeService;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;

    @PostConstruct
    public void init() {
        System.out.println("BookingServiceImpl created");
    }

    public BookingDTO bookTicket(BookingDTO bookingDTO, UserDetails user) {
        System.out.println("BookingService.bookTicket() called");

        List<SeatEntity> seat = seatService.getSeatEntityFromBooking(bookingDTO);
        UserEntity userEntity = userService.findUserByEmail(user.getUsername());
        TrainEntity train = trainService.getTrainEntityById(bookingDTO.getTrainId());
        RouteStationTimeEntity departureStation = routeStationTimeService.findRouteByNameStation(bookingDTO.getRouteId(), bookingDTO.getDepartureStation().getName());
        RouteStationTimeEntity arrivalStation = routeStationTimeService.findRouteByNameStation(bookingDTO.getRouteId(), bookingDTO.getArrivalStation().getName());
        List<Long> seatIds = seatService.getSeatsId(bookingDTO.getSeatsList());

        List<BookingEntity> conflicts = bookingRepository.findConflictingBookings(
                seatIds,
                bookingDTO.getTrainId(),
                bookingDTO.getTravelDate(),
                departureStation.getStopOrder(),
                arrivalStation.getStopOrder()
        );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Seat is already booked on this segment for this date and train");
        }

        BookingEntity booking = BookingEntity.builder()
                .seats(seat)
                .train(train)
                .route(departureStation.getRoute())
                .departureStation(departureStation.getStation())
                .arrivalStation(arrivalStation.getStation())
                .arrivalDate(bookingDTO.getTravelDate())
                .user(userEntity)
                .build();

        return bookingMapper.toBookingDTO(bookingRepository.save(booking));
    }
}
