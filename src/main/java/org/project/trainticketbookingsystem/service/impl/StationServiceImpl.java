package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.StationDTO;
import org.project.trainticketbookingsystem.entity.StationEntity;
import org.project.trainticketbookingsystem.mapper.StationMapper;
import org.project.trainticketbookingsystem.repository.StationRepository;
import org.project.trainticketbookingsystem.service.StationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public StationDTO addStation(StationDTO stationDTO) {
        StationEntity stationEntity = stationMapper.toStationEntity(stationDTO);
        if(isExistsStation(stationEntity))
            throw new RuntimeException("Station already exists");

        stationRepository.save(stationEntity);
        return stationMapper.toStationDTO(stationRepository.save(stationEntity));
    }

    @Override
    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    @Transactional
    @Override
    public StationEntity getStationByName(String stationName) {
        return stationRepository.findByName(stationName);
    }

    private boolean isExistsStation(StationEntity stationEntity) {
        return stationRepository.existsByName(stationEntity.getName());
    }
}
