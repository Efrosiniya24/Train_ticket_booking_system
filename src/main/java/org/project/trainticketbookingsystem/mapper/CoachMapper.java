package org.project.trainticketbookingsystem.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.trainticketbookingsystem.dto.CoachDto;
import org.project.trainticketbookingsystem.entity.CoachEntity;

@Mapper(componentModel = "spring", uses = SeatMapper.class)
public interface CoachMapper {
    @Mapping(source = "seats", target = "seats")
    CoachDto toCoachDTO(CoachEntity coachEntity);

    List<CoachDto> toCoachDTO(List<CoachEntity> coachEntities);
}
