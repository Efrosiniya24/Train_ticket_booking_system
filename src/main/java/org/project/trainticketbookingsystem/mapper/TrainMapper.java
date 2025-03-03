package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.TrainDTO;
import org.project.trainticketbookingsystem.entity.TrainEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainMapper {
    List<TrainDTO> toTrainDTO(List<TrainEntity> trainEntity);
    TrainDTO toTrainDTO(TrainEntity trainEntity);
    TrainEntity toTrainEntity(TrainDTO trainDTO);
}
