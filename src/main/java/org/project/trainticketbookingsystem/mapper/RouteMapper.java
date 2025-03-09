package org.project.trainticketbookingsystem.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.trainticketbookingsystem.dto.RouteDto;
import org.project.trainticketbookingsystem.entity.RouteEntity;

@Mapper(componentModel = "spring", uses = {RouteStationTimeMapper.class, TrainMapper.class})
public interface RouteMapper {

    @Mapping(target = "routeStationTimeDTO", source = "routeStationTime")
    @Mapping(target = "train", source = "train")
    RouteDto toRouteDTO(RouteEntity routeEntity);

    List<RouteDto> toRouteDTOList(List<RouteEntity> routeEntities);

    RouteEntity toRouteEntity(RouteDto routeDto);
}
