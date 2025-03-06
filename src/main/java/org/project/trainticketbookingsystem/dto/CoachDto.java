package org.project.trainticketbookingsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class CoachDto {
    private Long id;
    private int number;
    private int numberOfSeats;
    private TrainDto train;
    private List<SeatDto> seats;
}
