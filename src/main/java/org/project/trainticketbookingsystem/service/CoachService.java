package org.project.trainticketbookingsystem.service;

import java.util.List;
import org.project.trainticketbookingsystem.dto.CoachDto;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;

public interface CoachService {
    CoachDto createCoach(TrainEntity trainEntity, CoachDto coachDTO);

    void createCoachList(TrainEntity trainEntity, List<CoachDto> coachDtos);

    CoachDto toCoachDTO(CoachEntity coachEntity);

    void deleteCoach(List<CoachEntity> coachEntities);

    List<CoachEntity> update(List<CoachDto> coachDtoList);
}
