package org.project.trainticketbookingsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RouteStationTimeDTO {
    private Long id;
    private int stopOrder;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private StationDTO stationDTO;
}
