package org.project.trainticketbookingsystem.dto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrainDTO {
    private Long id;
    private String train;
    private List<CoachDTO> coachDTOList;
}
