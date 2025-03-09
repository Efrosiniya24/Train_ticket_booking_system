package org.project.trainticketbookingsystem.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteDto;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDto;
import org.project.trainticketbookingsystem.dto.SearchTicketDto;
import org.project.trainticketbookingsystem.dto.SegmentDto;
import org.project.trainticketbookingsystem.dto.StationDto;
import org.project.trainticketbookingsystem.entity.RouteEntity;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.exceptions.RouteException;
import org.project.trainticketbookingsystem.mapper.RouteMapper;
import org.project.trainticketbookingsystem.mapper.RouteStationTimeMapper;
import org.project.trainticketbookingsystem.mapper.StationMapper;
import org.project.trainticketbookingsystem.repository.RouteRepository;
import org.project.trainticketbookingsystem.service.RouteService;
import org.project.trainticketbookingsystem.service.RouteStationTimeService;
import org.project.trainticketbookingsystem.service.SeatService;
import org.project.trainticketbookingsystem.service.StationService;
import org.project.trainticketbookingsystem.service.TrainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteStationTimeService stationTimeService;
    private final StationService stationService;
    private final RouteRepository routeRepository;
    private final RouteStationTimeMapper routeStationTimeMapper;
    private final StationMapper stationMapper;
    private final TrainService trainService;
    private final RouteMapper routeMapper;
    private final SeatService seatService;

    @Transactional
    @Override
    public RouteDto createRoute(RouteDto routeDTO) {
        TrainEntity train = trainService.getTrainEntityById(routeDTO.getTrain().getId());

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
    public List<RouteDto> getAllRoutes() {
        List<RouteEntity> routeEntities = routeRepository.findAll();
        return routeMapper.toRouteDTOList(routeEntities);
    }

    @Override
    public List<RouteDto> searchRoutes(SearchTicketDto searchTicketDTO) {
        Long departureStationId = searchTicketDTO.getDepartureCityId();
        Long arrivalStationId = searchTicketDTO.getArrivalCityId();
        LocalDate departureDate = searchTicketDTO.getDepartureDate();

        List<RouteEntity> routeEntities = routeRepository.findAll().stream()
                .filter(route -> {
                    List<RouteStationTimeEntity> stations = route.getRouteStationTime();

                    Optional<RouteStationTimeEntity> departureStation = stations.stream()
                            .filter(station -> station.getStation().getId().equals(departureStationId))
                            .filter(station -> station.getDepartureDate().toLocalDate().equals(departureDate))
                            .findFirst();

                    Optional<RouteStationTimeEntity> arrivalStation = stations.stream()
                            .filter(station -> station.getStation().getId().equals(arrivalStationId))
                            .findFirst();

                    if (departureStation.isPresent() && arrivalStation.isPresent()) {
                        return departureStation.get().getStopOrder() < arrivalStation.get().getStopOrder();
                    }
                    return false;
                })
                .collect(Collectors.toList());
        return routeMapper.toRouteDTOList(routeEntities);
    }

    @Override
    public List<SegmentDto> getRequirementSegment(List<RouteDto> routeDTOs, SearchTicketDto searchTicketDTO) {
        StationDto departureStationDto = stationService.getStationById(searchTicketDTO.getDepartureCityId());
        StationDto arrivalStationDto = stationService.getStationById(searchTicketDTO.getArrivalCityId());

        return routeDTOs.stream()
                .map(route -> {
                    List<RouteStationTimeDto> stations = route.getRouteStationTimeDTO();

                    Optional<RouteStationTimeDto> departureStation = stations.stream()
                            .filter(station -> station.getStationDTO().getName().equals(departureStationDto.getName()))
                            .findFirst();

                    Optional<RouteStationTimeDto> arrivalStation = stations.stream()
                            .filter(station -> station.getStationDTO().getName().equals(arrivalStationDto.getName()))
                            .findFirst();

                    return SegmentDto.builder()
                            .trainDTO(route.getTrain())
                            .firstCityRoute(route.getRouteStationTimeDTO().get(0).getStationDTO().getName())
                            .lastCityRoute(route.getRouteStationTimeDTO().get(stations.size() - 1).getStationDTO().getName())
                            .routeDTO(route)
                            .departureCity(departureStationDto.getName())
                            .arrivalCity(arrivalStationDto.getName())
                            .departureDateTime(departureStation.get().getDepartureDate())
                            .arrivalDateTime(arrivalStation.get().getArrivalDate())
                            .timeRoad(formatDuration(Duration.between(departureStation.get().getDepartureDate(), arrivalStation.get().getArrivalDate())))
                            .price(seatService.getPrice(route.getTrain().getId()))
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public RouteDto getRouteById(Long id) {
        RouteEntity routeEntity = routeRepository.findById(id)
                .orElseThrow(() -> new RouteException("Route not found"));
        return routeMapper.toRouteDTO(routeEntity);
    }

    @Override
    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public RouteDto updateRoute(Long id, RouteDto routeDTO) {
        RouteEntity routeEntity = routeRepository.findById(id)
                .orElseThrow(() -> new RouteException("Route is not found"));

        TrainEntity train = trainService.getTrainEntityById(routeDTO.getTrain().getId());
        routeEntity.setTrain(train);

        List<RouteStationTimeEntity> updatedStations = stationTimeService.update(routeDTO.getRouteStationTimeDTO(), routeEntity);
        routeEntity.setRouteStationTime(updatedStations);

        routeRepository.save(routeEntity);
        return routeMapper.toRouteDTO(routeEntity);
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return (hours > 0 ? hours + "ч " : "") + minutes + "мин";
    }

}
