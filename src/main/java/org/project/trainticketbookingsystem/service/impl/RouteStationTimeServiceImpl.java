package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDTO;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;
import org.project.trainticketbookingsystem.entity.StationEntity;
import org.project.trainticketbookingsystem.mapper.RouteStationTimeMapper;
import org.project.trainticketbookingsystem.mapper.StationMapper;
import org.project.trainticketbookingsystem.repository.RouteStationTimeRepository;
import org.project.trainticketbookingsystem.service.RouteStationTimeService;
import org.project.trainticketbookingsystem.service.StationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteStationTimeServiceImpl implements RouteStationTimeService {
    private final RouteStationTimeRepository routeStationTimeRepository;
    private final RouteStationTimeMapper routeStationTimeMapper;
    private final StationService stationService;

    @Override
    public List<RouteStationTimeEntity> create(List<RouteStationTimeDTO> routeStationTimeDTO, RouteEntity routeEntity) {
        List<RouteStationTimeEntity> routeStationTimes = routeStationTimeDTO.stream()
                .map(dto -> {
                    RouteStationTimeEntity entity = routeStationTimeMapper.toRouteStationTimeEntity(dto);
                    entity.setRoute(routeEntity);

                    StationEntity station = stationService.getStationByName(dto.getStationDTO().getName());
                    entity.setStation(station);

                    return entity;
                })
                .collect(Collectors.toList());

            routeStationTimeRepository.saveAll(routeStationTimes);
        return routeStationTimes;
    }

    @Override
    public RouteStationTimeEntity findRouteByNameStation(Long routeId, String stationName) {
        return routeStationTimeRepository.findByIdAndStation_Name(routeId, stationName);
    }

    @Override
    public List<RouteStationTimeEntity> update(List<RouteStationTimeDTO> routeStationTimeDTOs, RouteEntity routeEntity) {
        List<RouteStationTimeEntity> updatedStations = routeStationTimeDTOs.stream()
                .map(dto -> {
                    RouteStationTimeEntity entity = routeStationTimeRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("not found"));
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
