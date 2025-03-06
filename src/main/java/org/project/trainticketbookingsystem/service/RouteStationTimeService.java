package org.project.trainticketbookingsystem.service;

import java.util.List;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDto;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;

public interface RouteStationTimeService {
    List<RouteStationTimeEntity> create(List<RouteStationTimeDto> routeStationTimeDtos, RouteEntity routeEntity);

    RouteStationTimeEntity findRouteByNameStation(Long routeId, String stationName);

    List<RouteStationTimeEntity> update(List<RouteStationTimeDto> routeStationTimeDTO, RouteEntity routeEntity);
}
