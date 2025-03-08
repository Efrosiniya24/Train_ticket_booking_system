package org.project.trainticketbookingsystem.service;

import java.util.List;
import java.util.Map;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDto;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;

public interface RouteStationTimeService {
    List<RouteStationTimeEntity> create(List<RouteStationTimeDto> routeStationTimeDtos, RouteEntity routeEntity);

    List<RouteStationTimeEntity> update(List<RouteStationTimeDto> routeStationTimeDTO, RouteEntity routeEntity);

    Map<String, RouteStationTimeDto> findByRouteIdAndStationId(Long routeId, Long departureStationId, Long arrivalStationId);
}
