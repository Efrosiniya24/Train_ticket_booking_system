package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.StationDTO;
import org.project.trainticketbookingsystem.entity.StationEntity;

import java.util.List;

public interface StationService {
    List<StationDTO> getAllStations();
    void addStation(StationDTO stationDTO);
    void deleteStation(Long id);
    StationEntity getStationByName(String stationName);
}
