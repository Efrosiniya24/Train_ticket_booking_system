package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.BookingDTO;
import org.project.trainticketbookingsystem.dto.SeatDTO;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.SeatEntity;

import java.util.List;

public interface SeatService {
    List<SeatDTO> createSeat(CoachEntity coachEntity, List<SeatDTO> seatDTOs);
    List<SeatEntity> getSeatEntityFromBooking(BookingDTO bookingDTO);
    List<Long> getSeatsId(List<SeatEntity> seatEntities);
    void deleteSeat(List<SeatEntity> seatEntity);
    List<SeatEntity> updateSeats(List<SeatEntity> seats);
}
