package org.project.trainticketbookingsystem.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.trainticketbookingsystem.dto.TrainDto;
import org.project.trainticketbookingsystem.entity.TrainEntity;

@Mapper(componentModel = "spring", uses = CoachMapper.class)
public interface TrainMapper {
    @Mapping(source = "coachEntities", target = "coachDtoList")
    TrainDto toTrainDTO(TrainEntity trainEntity);

    List<TrainDto> toTrainDTOList(List<TrainEntity> trainEntities);

    TrainEntity toTrainEntity(TrainDto trainDTO);
}
