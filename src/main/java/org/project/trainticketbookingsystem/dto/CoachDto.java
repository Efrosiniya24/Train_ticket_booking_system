package org.project.trainticketbookingsystem.dto;

import java.util.List;
import lombok.Data;

@Data
public class CoachDto {
    private Long id;
    private int number;
    private int numberOfSeats;
    private TrainDto train;
    private List<SeatDto> seats;
}
