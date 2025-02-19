package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.StationDTO;
import org.project.trainticketbookingsystem.entity.StationEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {
    List<StationDTO> toStationDTO(List<StationEntity> stationEntities);
    StationEntity toStationEntity(StationDTO stationDTO);
}
