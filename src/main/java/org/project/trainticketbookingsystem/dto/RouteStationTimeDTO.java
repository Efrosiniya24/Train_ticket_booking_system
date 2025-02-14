package org.project.trainticketbookingsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RouteStationTimeDTO {
    private long id;
    private int stopOrder;
    private LocalDateTime departure;
}
