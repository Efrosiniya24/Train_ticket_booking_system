package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.trainticketbookingsystem.dto.TrainDto;
import org.project.trainticketbookingsystem.entity.TrainEntity;

@Mapper(componentModel = "spring", uses = CoachMapper.class)
public interface TrainMapper {
    @Mapping(source = "coachEntities", target = "coachDtoList")
    TrainDto toTrainDTO(TrainEntity trainEntity);

    TrainEntity toTrainEntity(TrainDto trainDTO);
}
