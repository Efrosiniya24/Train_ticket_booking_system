package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.RouteStationTimeDTO;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;

import java.util.List;

public interface RouteStationTimeService {
    List<RouteStationTimeEntity> create(List<RouteStationTimeDTO> routeStationTimeDTOS, RouteEntity routeEntity);
    RouteStationTimeEntity findRouteByNameStation(Long routeId, String stationName);
}
