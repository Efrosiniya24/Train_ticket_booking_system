package org.project.trainticketbookingsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatStatusDtoResponse {
    private Long seatId;
    private Long coachId;
    private boolean isBooked;
}