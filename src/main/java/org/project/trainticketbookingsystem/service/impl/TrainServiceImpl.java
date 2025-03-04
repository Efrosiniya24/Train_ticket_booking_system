package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.CoachDTO;
import org.project.trainticketbookingsystem.dto.TrainDTO;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.TrainEntity;
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

    @Override
    public TrainDTO addTrain(TrainDTO train) {
        TrainEntity trainEntity = trainMapper.toTrainEntity(train);
        if (isExistTrain(trainEntity))
            throw new RuntimeException("Train is already exist");

        trainRepository.save(trainEntity);

        List<CoachDTO> coachDTOS = train.getCoachDTOList();

        for (CoachDTO coachDTO : coachDTOS) {
            coachService.createCoach(trainEntity, coachDTO);
        }
        return trainMapper.toTrainDTO(trainEntity);
    }

    @Override
    public List<TrainDTO> getAllTrains() {//структурав json
        List<TrainEntity> trainEntities = trainRepository.findAll();
        return trainEntities.stream()
                .map(trainEntity -> {
                    TrainDTO trainDTO = trainMapper.toTrainDTO(trainEntity);
                    trainDTO.setCoachDTOList(trainEntity.getCoachEntities().stream()
                            .map(coachService::toCoachDTO).toList());
                    return trainDTO;
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

    @Override
    public void deleteTrainById(Long id) {
        TrainEntity train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));
        coachService.deleteCoach(train.getCoachEntities());
        trainRepository.delete(train);
    }

    @Override
    public TrainDTO updateTrain(Long id, TrainDTO trainDTO) {
        TrainEntity trainEntity = trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));

        trainEntity.setId(id);
        trainEntity.setTrain(trainDTO.getTrain());
        trainEntity.setCoachEntities(coachService.update(trainEntity));

        trainRepository.save(trainEntity);
        return trainMapper.toTrainDTO(trainEntity);
    }

    private boolean isExistTrain(TrainEntity trainEntity) {
        return trainRepository.existsByTrain(trainEntity.getTrain());
    }
}
