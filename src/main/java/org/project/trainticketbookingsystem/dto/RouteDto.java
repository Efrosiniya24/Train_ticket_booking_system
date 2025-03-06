package org.project.trainticketbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDto {
    private Long id;
    private TrainDto train;
    private List<RouteStationTimeDto> routeStationTimeDTO;
}
