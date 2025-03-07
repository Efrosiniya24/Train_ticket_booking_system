package org.project.trainticketbookingsystem.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDto {
    private Long id;
    private TrainDto train;
    private List<RouteStationTimeDto> routeStationTimeDTO;
}
