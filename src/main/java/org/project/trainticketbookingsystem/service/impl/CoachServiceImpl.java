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

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public CoachDTO toCoachDTO(CoachEntity coachEntity) {
        return coachMapper.toCoachDTO(coachEntity);
    }

    @Override
    public void deleteCoach(List<CoachEntity> coachEntities) {
        for(CoachEntity coachEntity : coachEntities) {
            seatService.deleteSeat(coachEntity.getSeats());
        }

        coachRepository.deleteAll(coachEntities);
    }

    @Override
    public List<CoachEntity> update(List<CoachDTO> coachDTOList) {
        List<CoachEntity> coachEntities = coachDTOList.stream()
                .map(coachDTO -> {
                    CoachEntity coachEntity = coachRepository.findById(coachDTO.getId()).orElseThrow(() -> new RuntimeException("Coach not found"));
                    coachEntity.setId(coachDTO.getId());
                    coachEntity.setNumber(coachDTO.getNumber());
                    coachEntity.setNumberOfSeats(coachDTO.getNumberOfSeats());
                    coachEntity.setSeats(seatService.updateSeats(coachDTO.getSeats()));
                    return coachEntity;
                })
                .collect(Collectors.toList());

        coachRepository.saveAll(coachEntities);
        return coachEntities;
    }
}
