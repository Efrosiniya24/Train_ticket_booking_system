package org.project.trainticketbookingsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class CoachDTO {
    private Long id;
    private int number;
    private int numberOfSeats;
    private TrainDTO train;
    private List<SeatDTO> seats;
}
