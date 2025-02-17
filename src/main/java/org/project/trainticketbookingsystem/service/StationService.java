package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.StationDTO;

import java.util.List;

public interface StationService {
    List<StationDTO> getAllStations();

    void addStation(StationDTO stationDTO);

    void deleteStation(Long id);
}
