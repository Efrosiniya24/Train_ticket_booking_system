package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDTO;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteStationTimeMapper {
    List<RouteStationTimeEntity> toRouteStationTimeEntity(List<RouteStationTimeDTO> routeStationTimeDTO);
}
