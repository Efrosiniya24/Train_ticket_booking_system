package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.BookingDto;
import org.project.trainticketbookingsystem.dto.SeatDto;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.SeatEntity;

import java.util.List;

public interface SeatService {
    List<SeatDto> createSeat(CoachEntity coachEntity, List<SeatDto> seatDtos);
    List<SeatEntity> getSeatEntityFromBooking(BookingDto bookingDTO);
    List<Long> getSeatsId(List<SeatEntity> seatEntities);
    void deleteSeat(List<SeatEntity> seatEntity);
    List<SeatEntity> updateSeats(List<SeatDto> seats);
    double getPrice(Long trainId);
}
