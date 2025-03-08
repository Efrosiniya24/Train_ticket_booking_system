package org.project.trainticketbookingsystem.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDto;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;

@Mapper(componentModel = "spring", uses = StationMapper.class)
public interface RouteStationTimeMapper {
    RouteStationTimeEntity toRouteStationTimeEntity(RouteStationTimeDto routeStationTimeDTO);

    @Mapping(target = "stationDTO", source = "station")
    RouteStationTimeDto toRouteStationTimeDTO(RouteStationTimeEntity routeStationTimeEntity);

    List<RouteStationTimeEntity> toRouteStationTimeEntityList(List<RouteStationTimeDto> routeStationTimeDTOList);

    @Mapping(target = "stationDTO", source = "station")
    List<RouteStationTimeDto> toRouteStationTimeDtoList(List<RouteStationTimeEntity> routeStationTimeEntityList);
}


