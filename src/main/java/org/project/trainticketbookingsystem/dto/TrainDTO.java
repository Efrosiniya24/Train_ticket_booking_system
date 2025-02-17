package org.project.trainticketbookingsystem.dto;
import lombok.Data;

import java.util.List;

@Data
public class TrainDTO {
    private Long id;
    private String train;
    private List<CoachDTO> coachDTOList;
}
