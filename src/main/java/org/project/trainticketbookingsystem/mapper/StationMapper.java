package org.project.trainticketbookingsystem.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.StationDto;
import org.project.trainticketbookingsystem.entity.StationEntity;

@Mapper(componentModel = "spring")
public interface StationMapper {
    List<StationDto> toStationDTO(List<StationEntity> stationEntities);

    StationEntity toStationEntity(StationDto stationDTO);

    StationDto toStationDTO(StationEntity stationEntity);
}
