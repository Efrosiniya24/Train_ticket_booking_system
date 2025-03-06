package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.CoachDto;
import org.project.trainticketbookingsystem.dto.TrainDto;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.exceptions.TrainException;
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
    public TrainDto addTrain(TrainDto train) {
        TrainEntity trainEntity = trainMapper.toTrainEntity(train);
        if (isExistTrain(trainEntity))
            throw new TrainException("Train is already exist");

        trainRepository.save(trainEntity);

        List<CoachDto> coachDtos = train.getCoachDtoList();

        for (CoachDto coachDTO : coachDtos) {
            coachService.createCoach(trainEntity, coachDTO);
        }
        return trainMapper.toTrainDTO(trainEntity);
    }

    @Override
    public List<TrainDto> getAllTrains() {
        List<TrainEntity> trainEntities = trainRepository.findAll();
        return trainEntities.stream()
                .map(trainEntity -> {
                    TrainDto trainDTO = trainMapper.toTrainDTO(trainEntity);
                    trainDTO.setCoachDtoList(trainEntity.getCoachEntities().stream()
                            .map(coachService::toCoachDTO).toList());
                    return trainDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TrainDto getTrainById(Long id) {
        TrainEntity trainEntity = trainRepository.findById(id).orElseThrow(() -> new TrainException("Train not found"));
        return trainMapper.toTrainDTO(trainEntity);
    }

    @Override
    public int getNumberOfSeats(Long trainId) {
        TrainEntity trainEntity = trainRepository.findById(trainId).orElseThrow(() -> new TrainException("Train not found"));
        return trainEntity.getCoachEntities().stream()
                .mapToInt(coach -> coach.getSeats().size())
                .sum();
    }

    @Override
    public TrainEntity getTrainEntityById(Long id) {
        return trainRepository.findById(id).orElseThrow(() -> new TrainException("Train not found"));
    }

    @Override
    public void deleteTrainById(Long id) {
        TrainEntity train = trainRepository.findById(id)
                .orElseThrow(() -> new TrainException("Train not found"));
        coachService.deleteCoach(train.getCoachEntities());
        trainRepository.delete(train);
    }

    @Override
    public TrainDto updateTrain(Long id, TrainDto trainDTO) {
        TrainEntity trainEntity = trainRepository.findById(id).orElseThrow(() -> new TrainException("Train not found"));

        trainEntity.setId(id);
        trainEntity.setTrain(trainDTO.getTrain());
        trainEntity.setCoachEntities(coachService.update(trainDTO.getCoachDtoList()));

        trainRepository.save(trainEntity);
        return trainMapper.toTrainDTO(trainEntity);
    }

    private boolean isExistTrain(TrainEntity trainEntity) {
        return trainRepository.existsByTrain(trainEntity.getTrain());
    }
}
