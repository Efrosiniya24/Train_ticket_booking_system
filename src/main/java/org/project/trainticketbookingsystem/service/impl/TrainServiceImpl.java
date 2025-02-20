package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.CoachDTO;
import org.project.trainticketbookingsystem.dto.TrainDTO;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.mapper.TrainMapper;
import org.project.trainticketbookingsystem.repository.TrainRepository;
import org.project.trainticketbookingsystem.service.CoachService;
import org.project.trainticketbookingsystem.service.TrainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;
    private final CoachService coachService;

    @Override
    public TrainDTO addTrain(TrainDTO train) {
        TrainEntity trainEntity = new TrainEntity();
        trainEntity.setTrain(train.getTrain());
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
        return trainMapper.toTrainDTO(trainEntities);
    }

    @Override
    public TrainDTO getTrainById(Long id) {
        TrainEntity trainEntity = trainRepository.findById(id).get();
        return trainMapper.toTrainDTO(trainEntity);
    }
}
