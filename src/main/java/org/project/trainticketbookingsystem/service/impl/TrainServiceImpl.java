package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.CoachDTO;
import org.project.trainticketbookingsystem.dto.TrainDTO;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.mapper.CoachMapper;
import org.project.trainticketbookingsystem.mapper.TrainMapper;
import org.project.trainticketbookingsystem.repository.TrainRepository;
import org.project.trainticketbookingsystem.service.CoachService;
import org.project.trainticketbookingsystem.service.TrainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;
    private final CoachService coachService;
    private final CoachMapper coachMapper;

    @Override
    public TrainDTO addTrain(TrainDTO train) {
        TrainEntity trainEntity = trainMapper.toTrainEntity(train);
        trainRepository.save(trainEntity);

        List<CoachDTO> coachDTOS = train.getCoachDTOList();

        for (CoachDTO coachDTO : coachDTOS) {
            coachService.createCoach(trainEntity, coachDTO);
        }
        return trainMapper.toTrainDTO(trainEntity);
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        List<TrainEntity> trainEntities = trainRepository.findAll();
        return trainEntities.stream()
                .map(trainEntity -> {
                    return TrainDTO.builder()
                            .id(trainEntity.getId())
                            .train(trainEntity.getTrain())
                            .coachDTOList(trainEntity.getCoachEntities().stream()
                                    .map(coachService::toCoachDTO).toList())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public TrainDTO getTrainById(Long id) {
        TrainEntity trainEntity = trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
        return trainMapper.toTrainDTO(trainEntity);
    }

    @Override
    public int getNumberOfSeats(Long trainId) {
        TrainEntity trainEntity = trainRepository.findById(trainId).orElseThrow(() -> new RuntimeException("Train not found"));
        return trainEntity.getCoachEntities().stream()
                .mapToInt(coach -> coach.getSeats().size())
                .sum();
    }

    @Override
    public TrainEntity getTrainEntityById(Long id) {
        return trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
    }
}
