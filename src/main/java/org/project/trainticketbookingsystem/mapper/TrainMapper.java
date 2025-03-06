package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.TrainDto;
import org.project.trainticketbookingsystem.entity.TrainEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainMapper {
    List<TrainDto> toTrainDTO(List<TrainEntity> trainEntity);
    TrainDto toTrainDTO(TrainEntity trainEntity);
    TrainEntity toTrainEntity(TrainDto trainDTO);
}
