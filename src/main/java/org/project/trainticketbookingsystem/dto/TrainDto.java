package org.project.trainticketbookingsystem.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainDto {
    private Long id;
    private String train;
    private List<CoachDto> coachDtoList;
}
