package org.project.trainticketbookingsystem.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.TrainDto;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.exceptions.TrainException;
import org.project.trainticketbookingsystem.mapper.TrainMapper;
import org.project.trainticketbookingsystem.repository.TrainRepository;
import org.project.trainticketbookingsystem.service.CoachService;
import org.project.trainticketbookingsystem.service.TrainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;
    private final CoachService coachService;

    @Transactional
    @Override
    public TrainDto addTrain(TrainDto train) {
        TrainEntity trainEntity = trainMapper.toTrainEntity(train);
        if (isExistTrain(trainEntity))
            throw new TrainException("Train is already exist");

        trainRepository.save(trainEntity);
        coachService.createCoachList(trainEntity, train.getCoachDtoList());

        return trainMapper.toTrainDTO(trainEntity);
    }

    @Override
    public List<TrainDto> getAllTrains() {
        return trainMapper.toTrainDTOList(trainRepository.findAll());
    }

    @Override
    public TrainDto getTrainById(Long trainId) {
        TrainEntity trainEntity = trainRepository.findById(trainId).orElseThrow(() -> new TrainException("Train not found"));
        return trainMapper.toTrainDTO(trainEntity);
    }

    @Override
    public int getNumberOfSeats(Long trainId) {
        TrainEntity trainEntity = trainMapper.toTrainEntity(getTrainById(trainId));
        return trainEntity.getCoachEntities().stream()
                .mapToInt(coach -> coach.getSeats().size())
                .sum();
    }

    @Override
    public void deleteTrainById(Long trainId) {
        TrainEntity train = trainMapper.toTrainEntity(getTrainById(trainId));
        coachService.deleteCoach(train.getCoachEntities());
        trainRepository.delete(train);
    }

    @Override
    public TrainDto updateTrain(Long trainId, TrainDto trainDTO) {
        TrainEntity trainEntity = trainMapper.toTrainEntity(getTrainById(trainId));

        trainEntity.setId(trainId);
        trainEntity.setTrain(trainDTO.getTrain());
        trainEntity.setCoachEntities(coachService.update(trainDTO.getCoachDtoList()));
        trainEntity.setNumberOfCoaches(trainDTO.getNumberOfCoaches());

        trainRepository.save(trainEntity);
        return trainMapper.toTrainDTO(trainEntity);
    }

    private boolean isExistTrain(TrainEntity trainEntity) {
        return trainRepository.existsByTrain(trainEntity.getTrain());
    }
}
