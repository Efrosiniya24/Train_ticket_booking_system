package org.project.trainticketbookingsystem.service;

import java.util.List;
import org.project.trainticketbookingsystem.dto.SeatDto;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.SeatEntity;

public interface SeatService {
    List<SeatDto> createSeat(CoachEntity coachEntity, List<SeatDto> seatDtos);

    void deleteSeat(List<SeatEntity> seatEntity);

    List<SeatEntity> updateSeats(List<SeatDto> seats);

    double getPrice(Long trainId);

    SeatEntity getSeatById(Long seatId);
}
