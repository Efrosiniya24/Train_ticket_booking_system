package org.project.trainticketbookingsystem.service;

import java.util.List;
import org.project.trainticketbookingsystem.dto.StationDto;
import org.project.trainticketbookingsystem.entity.StationEntity;

public interface StationService {
    List<StationDto> getAllStations();

    StationDto addStation(StationDto stationDTO);

    void deleteStation(Long id);

    StationEntity getStationByName(String stationName);

    StationDto getStationById(Long id);
}
