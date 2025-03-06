package org.project.trainticketbookingsystem.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.StationDto;
import org.project.trainticketbookingsystem.entity.StationEntity;
import org.project.trainticketbookingsystem.exceptions.StationException;
import org.project.trainticketbookingsystem.mapper.StationMapper;
import org.project.trainticketbookingsystem.repository.StationRepository;
import org.project.trainticketbookingsystem.service.StationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Override
    public List<StationDto> getAllStations() {
        List<StationEntity> stations = stationRepository.findAll();
        return stationMapper.toStationDTO(stations);
    }

    @Override
    public StationDto addStation(StationDto stationDTO) {
        StationEntity stationEntity = stationMapper.toStationEntity(stationDTO);
        if (isExistsStation(stationEntity))
            throw new StationException("Station already exists");

        return stationMapper.toStationDTO(stationRepository.save(stationEntity));
    }

    @Override
    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    @Override
    public StationEntity getStationByName(String stationName) {
        StationEntity stationEntity = stationRepository.findByName(stationName);
        if (stationEntity == null)
            throw new StationException("Station does not exist");

        return stationRepository.findByName(stationName);
    }

    @Override
    public StationDto getStationById(Long id) {
        StationEntity stationEntity = stationRepository.findById(id).orElseThrow(() -> new StationException("Station does not exist"));
        return stationMapper.toStationDTO(stationEntity);
    }

    private boolean isExistsStation(StationEntity stationEntity) {
        return stationRepository.existsByName(stationEntity.getName());
    }
}
