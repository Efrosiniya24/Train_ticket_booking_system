package org.project.trainticketbookingsystem.service;

import java.util.List;
import org.project.trainticketbookingsystem.dto.RouteDto;
import org.project.trainticketbookingsystem.dto.SearchTicketDto;
import org.project.trainticketbookingsystem.dto.SegmentDto;

public interface RouteService {
    RouteDto createRoute(RouteDto routDTO);

    List<RouteDto> getAllRoutes();

    List<RouteDto> searchRoutes(SearchTicketDto searchTicketDTO);

    List<SegmentDto> getRequirementSegment(List<RouteDto> routeDTOs, SearchTicketDto searchTicketDTO);

    RouteDto getRouteById(Long id);

    void deleteRoute(Long id);

    RouteDto updateRoute(Long id, RouteDto routeDTO);
}
