package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteDTO;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.mapper.RouteMapper;
import org.project.trainticketbookingsystem.repository.RouteRepository;
import org.project.trainticketbookingsystem.repository.TrainRepository;
import org.project.trainticketbookingsystem.service.RouteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteMapper routeMapper;
    private final RouteStationTimeServiceImpl stationTimeService;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;

    @Transactional
    @Override
    public RouteDTO createRoute(RouteDTO routeDTO) {
        TrainEntity train = trainRepository.findById(routeDTO.getTrainId()).orElseThrow(() -> new RuntimeException("Train is not found"));

        RouteEntity routeEntity = RouteEntity.builder()
                .train(train)
                .build();

        List<RouteStationTimeEntity> stationTimeEntities = stationTimeService.create(routeDTO.getRouteStationTimeDTO(), routeEntity);

        routeEntity.setRouteStationTime(stationTimeEntities);

        routeRepository.save(routeEntity);
        return routeDTO;
    }

    @Override
    public List<RouteDTO> getAllRoutes() {
        List<RouteEntity> routeEntities = routeRepository.findAll();
        return routeMapper.toRouteDTO(routeEntities);
    }
}
