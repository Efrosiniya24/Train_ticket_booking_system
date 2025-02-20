package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteDTO;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDTO;
import org.project.trainticketbookingsystem.dto.SearchTicketDTO;
import org.project.trainticketbookingsystem.dto.SegmentDTO;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.mapper.RouteStationTimeMapper;
import org.project.trainticketbookingsystem.mapper.StationMapper;
import org.project.trainticketbookingsystem.repository.RouteRepository;
import org.project.trainticketbookingsystem.repository.TrainRepository;
import org.project.trainticketbookingsystem.service.RouteService;
import org.project.trainticketbookingsystem.service.RouteStationTimeService;
import org.project.trainticketbookingsystem.service.TrainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteStationTimeService stationTimeService;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final RouteStationTimeMapper routeStationTimeMapper;
    private final StationMapper stationMapper;
    private final TrainService trainService;

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
        return toRouteDTO(routeEntities);
    }

    @Override
    public List<RouteDTO> searchRoutes(SearchTicketDTO searchTicketDTO) {
        String departureCity = searchTicketDTO.getDepartureCity();
        String arrivalCity = searchTicketDTO.getArrivalCity();
        LocalDate departureDate = searchTicketDTO.getDepartureDate();

        List<RouteEntity> routeEntities = routeRepository.findAll().stream()
                .filter(route -> {
                    List<RouteStationTimeEntity> stations = route.getRouteStationTime();

                    Optional<RouteStationTimeEntity> departureStation = stations.stream()
                            .filter(station -> station.getStation().getName().equals(departureCity))
                            .findFirst();

                    Optional<RouteStationTimeEntity> arrivalStation = stations.stream()
                            .filter(station -> station.getStation().getName().equals(arrivalCity))
                            .findFirst();

                    boolean hasCorrectDate = stations.stream()
                            .anyMatch(station -> station.getDepartureDate().toLocalDate().equals(departureDate));

                    if (departureStation.isPresent() && arrivalStation.isPresent() && hasCorrectDate) {
                        return departureStation.get().getStopOrder() < arrivalStation.get().getStopOrder();
                    }
                    return false;
                })
                .collect(Collectors.toList());
        return toRouteDTO(routeEntities);
    }

    @Override
    public List<RouteDTO> toRouteDTO(List<RouteEntity> routeEntities) {

        List<RouteDTO> routeDTOS = routeEntities.stream()
                .map(routeEntity -> {
                    RouteDTO routeDTO = RouteDTO.builder()
                            .trainId(routeEntity.getTrain().getId())
                            .routeStationTimeDTO(routeEntity.getRouteStationTime().stream()
                                    .map(routeStationTime -> {
                                        RouteStationTimeDTO routeStationTimeDTO = routeStationTimeMapper.toRouteStationTimeDTO(routeStationTime);
                                        routeStationTimeDTO.setStationDTO(stationMapper.toStationDTO(routeStationTime.getStation()));
                                        return routeStationTimeDTO;
                                    })
                                    .collect(Collectors.toList()))
                            .build();
                    return routeDTO;
                })
                .collect(Collectors.toList());

        return routeDTOS;
    }

    @Override
    public List<SegmentDTO> getRequirementSegment(List<RouteDTO> routeDTOs, SearchTicketDTO searchTicketDTO) {
        return routeDTOs.stream()
                .map(route -> {
                    List<RouteStationTimeDTO> stations = route.getRouteStationTimeDTO();

                    Optional<RouteStationTimeDTO> departureStation = stations.stream()
                            .filter(station -> station.getStationDTO().getName().equals(searchTicketDTO.getDepartureCity()))
                            .findFirst();

                    Optional<RouteStationTimeDTO> arrivalStation = stations.stream()
                            .filter(station -> station.getStationDTO().getName().equals(searchTicketDTO.getArrivalCity()))
                            .findFirst();

                    SegmentDTO segmentDTO = SegmentDTO.builder()
                            .trainDTO(trainService.getTrainById(route.getTrainId()))
                            .firstCityRoute(route.getRouteStationTimeDTO().get(0).getStationDTO().getName())
                            .lastCityRoute(route.getRouteStationTimeDTO().get(stations.size() - 1).getStationDTO().getName())
                            .routeDTO(route)
                            .departureCity(searchTicketDTO.getDepartureCity())
                            .arrivalCity(searchTicketDTO.getArrivalCity())
                            .departureDateTime(departureStation.get().getDepartureDate())
                            .arrivalDateTime(arrivalStation.get().getArrivalDate())
                            .build();
                    return segmentDTO;
                })
                .collect(Collectors.toList());
    }
}
