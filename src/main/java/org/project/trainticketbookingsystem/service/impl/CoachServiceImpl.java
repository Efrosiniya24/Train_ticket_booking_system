package org.project.trainticketbookingsystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.CoachDto;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.exceptions.CoachException;
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
    public CoachDto createCoach(TrainEntity trainEntity, CoachDto coachDTO) {
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
    public void createCoachList(TrainEntity trainEntity, List<CoachDto> coachDtos) {
        for (CoachDto coachDTO : coachDtos) {
            createCoach(trainEntity, coachDTO);
        }
    }

    @Override
    public void deleteCoach(List<CoachEntity> coachEntities) {
        for (CoachEntity coachEntity : coachEntities) {
            getCoach(coachEntity.getId());
            seatService.deleteSeat(coachEntity.getSeats());
        }

        coachRepository.deleteAll(coachEntities);
    }

    @Override
    public List<CoachEntity> update(List<CoachDto> coachDtoList) {
        List<CoachEntity> coachEntities = coachDtoList.stream()
                .map(coachDTO -> {
                    CoachEntity coachEntity = coachMapper.toCoachEntity(getCoach(coachDTO.getId()));
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

    @Override
    public List<CoachDto> getCoachList(Long trainId) {
        List<CoachEntity> coaches = coachRepository.findByTrain_Id(trainId);
        return coachMapper.toCoachDTO(coaches);
    }

    @Override
    public CoachDto getCoach(Long coachId) {
        CoachEntity coachEntity = coachRepository.findById(coachId).orElseThrow(() -> new CoachException("Coach not found"));
        return coachMapper.toCoachDTO(coachEntity);
    }
}
