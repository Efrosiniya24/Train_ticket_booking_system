package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.RouteDTO;
import org.project.trainticketbookingsystem.dto.SearchTicketDTO;
import org.project.trainticketbookingsystem.entity.RouteEntity;

import java.util.List;

public interface RouteService {
    RouteDTO createRoute(RouteDTO routDTO);
    List<RouteDTO> getAllRoutes();
    List<RouteDTO> searchRoutes(SearchTicketDTO searchTicketDTO);
    List<RouteDTO> toRouteDTO(List<RouteEntity> routeEntities);
}
