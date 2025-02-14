package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDTO;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;
import org.project.trainticketbookingsystem.mapper.RouteStationTimeMapper;
import org.project.trainticketbookingsystem.repository.RouteStationTimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteStationTimeServiceImpl {
    private final RouteStationTimeRepository routeStationTimeRepository;
    private final RouteStationTimeMapper routeStationTimeMapper;

    public List<RouteStationTimeEntity> create(List<RouteStationTimeDTO> routeStationTimeDTO, RouteEntity routeEntity) {
        List<RouteStationTimeEntity> routeStationTime = routeStationTimeMapper.toRouteStationTimeEntity(routeStationTimeDTO);

        routeStationTime
                .forEach(routeStation -> routeStation.setRoute(routeEntity));

        routeStationTimeRepository.saveAll(routeStationTime);
        return routeStationTime;
    }
}
