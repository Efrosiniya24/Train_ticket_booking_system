package org.project.trainticketbookingsystem.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatStatusDtoResponse {
    private Map<Long, CoachSeatInfo> coachDtos;
    private int freeSeats;
}