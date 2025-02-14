package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.RouteDTO;

import java.util.List;

public interface RouteService {
    RouteDTO createRoute(RouteDTO routDTO);
    List<RouteDTO> getAllRoutes();
}
