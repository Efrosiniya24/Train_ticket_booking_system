package org.project.trainticketbookingsystem.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SegmentDto {
    private int segmentID;
    private TrainDto trainDTO;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String departureCity;
    private String arrivalCity;
    private RouteDto routeDTO;
    private String firstCityRoute;
    private String lastCityRoute;
    private String timeRoad;
    private double price;
}
