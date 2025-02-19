package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteDTO;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.mapper.RouteMapper;
import org.project.trainticketbookingsystem.repository.RouteRepository;
import org.project.trainticketbookingsystem.repository.TrainRepository;
import org.project.trainticketbookingsystem.service.RouteService;
import org.project.trainticketbookingsystem.service.RouteStationTimeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteMapper routeMapper;
    private final RouteStationTimeService stationTimeService;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;

    @Transactional
    @Override
    public RouteDTO createRoute(RouteDTO routeDTO) {
        TrainEntity train = trainRepository.findById(routeDTO.getTrainId()).orElseThrow(() -> new RuntimeException("Train is not found"));

        RouteEntity routeEntity = RouteEntity.builder()
                .train(train)
                .build();

        routeRepository.save(routeEntity);

        stationTimeService.create(routeDTO.getRouteStationTimeDTO(), routeEntity);

        return routeDTO;
    }

    @Override
    public List<RouteDTO> getAllRoutes() {
        List<RouteEntity> routeEntities = routeRepository.findAll();
        return routeMapper.toRouteDTO(routeEntities);
    }
}
