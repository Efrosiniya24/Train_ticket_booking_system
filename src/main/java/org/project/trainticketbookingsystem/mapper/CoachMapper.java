package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.CoachDTO;
import org.project.trainticketbookingsystem.entity.CoachEntity;

@Mapper(componentModel = "spring")
public interface CoachMapper {
    CoachDTO toCoachDTO(CoachEntity coachEntity);
}
