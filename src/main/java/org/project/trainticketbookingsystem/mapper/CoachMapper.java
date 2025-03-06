package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.CoachDto;
import org.project.trainticketbookingsystem.entity.CoachEntity;

@Mapper(componentModel = "spring")
public interface CoachMapper {
    CoachDto toCoachDTO(CoachEntity coachEntity);
}
