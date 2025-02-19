package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDTO;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;

@Mapper(componentModel = "spring")
public interface RouteStationTimeMapper {
    RouteStationTimeEntity toRouteStationTimeEntity(RouteStationTimeDTO routeStationTimeDTO);
}
