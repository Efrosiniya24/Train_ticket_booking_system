package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.RouteDTO;
import org.project.trainticketbookingsystem.entity.RouteEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    List<RouteDTO> toRouteDTO(List<RouteEntity> routeEntities);
}
