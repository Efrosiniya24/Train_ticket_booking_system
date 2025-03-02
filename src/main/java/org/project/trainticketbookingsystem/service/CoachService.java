package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.CoachDTO;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;

public interface CoachService {
    CoachDTO createCoach(TrainEntity trainEntity, CoachDTO coachDTO);
    CoachDTO toCoachDTO(CoachEntity coachEntity);
}
