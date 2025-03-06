package org.project.trainticketbookingsystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDto;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;
import org.project.trainticketbookingsystem.entity.StationEntity;
import org.project.trainticketbookingsystem.exceptions.RouteStationTimeException;
import org.project.trainticketbookingsystem.mapper.RouteStationTimeMapper;
import org.project.trainticketbookingsystem.repository.RouteStationTimeRepository;
import org.project.trainticketbookingsystem.service.RouteStationTimeService;
import org.project.trainticketbookingsystem.service.StationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteStationTimeServiceImpl implements RouteStationTimeService {
    private final RouteStationTimeRepository routeStationTimeRepository;
    private final RouteStationTimeMapper routeStationTimeMapper;
    private final StationService stationService;

    @Override
    public List<RouteStationTimeEntity> create(List<RouteStationTimeDto> routeStationTimeDTO, RouteEntity routeEntity) {
        List<RouteStationTimeEntity> routeStationTimeEntities = routeStationTimeMapper.toRouteStationTimeEntityList(routeStationTimeDTO);

        routeStationTimeEntities.forEach(route -> {
            route.setRoute(routeEntity);
            StationEntity station = stationService.getStationByName(route.getStation().getName());
            route.setStation(station);
        });

        return routeStationTimeRepository.saveAll(routeStationTimeEntities);
    }

    @Override
    public RouteStationTimeEntity findRouteByNameStation(Long routeId, String stationName) {
        return routeStationTimeRepository.findByIdAndStation_Name(routeId, stationName);
    }

    @Override
    public List<RouteStationTimeEntity> update(List<RouteStationTimeDto> routeStationTimeDtos, RouteEntity routeEntity) {
        List<RouteStationTimeEntity> updatedStations = routeStationTimeDtos.stream()
                .map(dto -> {
                    RouteStationTimeEntity entity = routeStationTimeRepository.findById(dto.getId()).orElseThrow(() -> new RouteStationTimeException(" Route not found"));
                    entity.setStopOrder(dto.getStopOrder());
                    entity.setDepartureDate(dto.getDepartureDate());
                    entity.setArrivalDate(dto.getArrivalDate());
                    entity.setStation(stationService.getStationByName(dto.getStationDTO().getName()));
                    entity.setRoute(routeEntity);
                    return entity;
                })
                .collect(Collectors.toList());

        return routeStationTimeRepository.saveAll(updatedStations);
    }


}
