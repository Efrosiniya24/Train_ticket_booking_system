package org.project.trainticketbookingsystem.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RouteStationTimeDto {
    private Long id;
    private int stopOrder;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private StationDto stationDTO;
    private RouteDto routeDTO;
}
