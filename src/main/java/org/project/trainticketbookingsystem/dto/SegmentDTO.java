package org.project.trainticketbookingsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SegmentDTO {
    private int segmentID;
    private TrainDTO trainDTO;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String departureCity;
    private String arrivalCity;
    private RouteDTO routeDTO;
    private String firstCityRoute;
    private String lastCityRoute;
}
