package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.CoachDTO;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.mapper.CoachMapper;
import org.project.trainticketbookingsystem.repository.CoachRepository;
import org.project.trainticketbookingsystem.service.CoachService;
import org.project.trainticketbookingsystem.service.SeatService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachMapper coachMapper;
    private final CoachRepository coachRepository;
    private final SeatService seatService;

    @Override
    public CoachDTO createCoach(TrainEntity trainEntity, CoachDTO coachDTO) {
        CoachEntity coachEntity = CoachEntity.builder()
                .number(coachDTO.getNumber())
                .numberOfSeats(coachDTO.getNumberOfSeats())
                .train(trainEntity)
                .build();
        coachRepository.save(coachEntity);
        seatService.createSeat(coachEntity, coachDTO.getSeats());


        return coachMapper.toCoachDTO(coachEntity);
    }
}
