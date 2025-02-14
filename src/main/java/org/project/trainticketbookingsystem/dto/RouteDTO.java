package org.project.trainticketbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {
    private Long trainId;
    private List<RouteStationTimeDTO> routeStationTimeDTO;
}
