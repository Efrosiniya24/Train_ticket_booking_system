package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.CoachDto;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;

import java.util.List;

public interface CoachService {
    CoachDto createCoach(TrainEntity trainEntity, CoachDto coachDTO);
    CoachDto toCoachDTO(CoachEntity coachEntity);
    void deleteCoach(List<CoachEntity> coachEntities);
    List<CoachEntity> update(List<CoachDto> coachDtoList);
}
