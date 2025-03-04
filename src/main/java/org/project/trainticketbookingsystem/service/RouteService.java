package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.RouteDTO;
import org.project.trainticketbookingsystem.dto.SearchTicketDTO;
import org.project.trainticketbookingsystem.dto.SegmentDTO;
import org.project.trainticketbookingsystem.entity.RouteEntity;

import java.util.List;

public interface RouteService {
    RouteDTO createRoute(RouteDTO routDTO);
    List<RouteDTO> getAllRoutes();
    List<RouteDTO> searchRoutes(SearchTicketDTO searchTicketDTO);
    List<RouteDTO> toRouteDTOList(List<RouteEntity> routeEntities);
    List<SegmentDTO> getRequirementSegment(List<RouteDTO> routeDTOs, SearchTicketDTO searchTicketDTO);
    RouteDTO getRouteById(Long id);
    RouteDTO toRouteDTO(RouteEntity routeEntity);
    void deleteRoute(Long id);
    RouteDTO updateRoute(Long id, RouteDTO routeDTO);
}
