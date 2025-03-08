package org.project.trainticketbookingsystem.dto;

import lombok.Data;

@Data
public class GetSeatStatusDto {
    private Long trainId;
    private Long routeId;
    private Long departureStationId;
    private Long arrivalStationId;
}
