package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteDTO;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDTO;
import org.project.trainticketbookingsystem.dto.StationDTO;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.mapper.RouteMapper;
import org.project.trainticketbookingsystem.mapper.RouteStationTimeMapper;
import org.project.trainticketbookingsystem.mapper.StationMapper;
import org.project.trainticketbookingsystem.repository.RouteRepository;
import org.project.trainticketbookingsystem.repository.TrainRepository;
import org.project.trainticketbookingsystem.service.RouteService;
import org.project.trainticketbookingsystem.service.RouteStationTimeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteStationTimeService stationTimeService;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final RouteStationTimeMapper routeStationTimeMapper;
    private final StationMapper stationMapper;

    @Transactional
    @Override
    public RouteDTO createRoute(RouteDTO routeDTO) {
        TrainEntity train = trainRepository.findById(routeDTO.getTrainId()).orElseThrow(() -> new RuntimeException("Train is not found"));

        RouteEntity routeEntity = RouteEntity.builder()
                .train(train)
                .build();

        routeRepository.save(routeEntity);

        List<RouteStationTimeEntity> routeStationTimeEntities = stationTimeService.create(routeDTO.getRouteStationTimeDTO(), routeEntity);
        routeEntity.setRouteStationTime(routeStationTimeEntities);
        routeRepository.save(routeEntity);
        return routeDTO;
    }

    @Override
    public List<RouteDTO> getAllRoutes() {
        List<RouteEntity> routeEntities = routeRepository.findAll();

        List<RouteDTO> routeDTOS = routeEntities.stream()
                .map(routeEntity -> {
                    RouteDTO routeDTO = RouteDTO.builder()
                            .trainId(routeEntity.getTrain().getId())
                            .routeStationTimeDTO(routeEntity.getRouteStationTime().stream()
                                    .map(routeStationTime -> {
                                        RouteStationTimeDTO routeStationTimeDTO = routeStationTimeMapper.toRouteStationTimeDTO(routeStationTime);
                                        routeStationTimeDTO.setStationDTO((StationDTO) stationMapper.toStationDTO(routeStationTime.getStation()));
                                        return routeStationTimeDTO;
                                    })
                                    .collect(Collectors.toList()))
                            .build();
                    return routeDTO;
                })
                .collect(Collectors.toList());

        return routeDTOS;
    }
}
