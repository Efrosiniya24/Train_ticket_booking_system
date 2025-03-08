package org.project.trainticketbookingsystem.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoachSeatInfo {
    private Long coachId;
    private List<SeatInfo> seats;
    private int freeSeats;
}