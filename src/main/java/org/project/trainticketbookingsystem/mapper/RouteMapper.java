package org.project.trainticketbookingsystem.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.trainticketbookingsystem.dto.RouteDto;
import org.project.trainticketbookingsystem.entity.RouteEntity;

@Mapper(componentModel = "spring", uses = {RouteStationTimeMapper.class, TrainMapper.class})
public interface RouteMapper {

    @Mapping(target = "routeStationTimeDTO", source = "routeStationTime")
    @Mapping(target = "train", source = "train")
    RouteDto toRouteDTO(RouteEntity routeEntity);

    default List<RouteDto> toRouteDTOList(List<RouteEntity> routeEntities) {
        return routeEntities.stream()
                .map(this::toRouteDTO)
                .collect(Collectors.toList());
    }

    RouteEntity toRouteEntity(RouteDto routeDto);
}
