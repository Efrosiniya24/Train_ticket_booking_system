package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.StationDTO;
import org.project.trainticketbookingsystem.entity.StationEntity;
import org.project.trainticketbookingsystem.mapper.StationMapper;
import org.project.trainticketbookingsystem.repository.StationRepository;
import org.project.trainticketbookingsystem.service.StationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Override
    public List<StationDTO> getAllStations() {
        List<StationEntity> stations = stationRepository.findAll();
        return stationMapper.toStationDTO(stations);
    }
}
