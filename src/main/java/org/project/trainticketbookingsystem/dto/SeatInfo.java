package org.project.trainticketbookingsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatInfo {
    private Long seatId;
    private boolean booked;
}