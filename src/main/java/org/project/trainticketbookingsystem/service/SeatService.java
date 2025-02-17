package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.SeatDTO;
import org.project.trainticketbookingsystem.entity.CoachEntity;

import java.util.List;

public interface SeatService {
    List<SeatDTO> createSeat(CoachEntity coachEntity, List<SeatDTO> seatDTOs);
}
