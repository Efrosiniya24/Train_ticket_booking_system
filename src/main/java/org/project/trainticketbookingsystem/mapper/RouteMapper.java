package org.project.trainticketbookingsystem.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.trainticketbookingsystem.dto.RouteDto;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDto;
import org.project.trainticketbookingsystem.dto.TrainDto;
import org.project.trainticketbookingsystem.entity.RouteEntity;

@Mapper(componentModel = "spring", uses = RouteStationTimeMapper.class)
public interface RouteMapper {

    @Mapping(target = "routeStationTimeDTO", source = "routeStationTime")
    RouteDto toRouteDTO(RouteEntity routeEntity);

    default List<RouteDto> toRouteDTOList(List<RouteEntity> routeEntities) {
        return routeEntities.stream()
                .map(this::toRouteDTO)
                .collect(Collectors.toList());
    }

    default RouteDto toRouteDTO(Long routeId, TrainDto trainDto, List<RouteStationTimeDto> routeStationTimeDtos) {
        return RouteDto.builder()
                .id(routeId)
                .train(trainDto)
                .routeStationTimeDTO(routeStationTimeDtos)
                .build();
    }

    RouteEntity toRouteEntity(RouteDto routeDto);
}
