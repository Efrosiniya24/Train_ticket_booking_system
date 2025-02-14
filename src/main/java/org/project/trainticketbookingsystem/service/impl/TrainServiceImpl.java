package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.TrainDTO;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.project.trainticketbookingsystem.mapper.TrainMapper;
import org.project.trainticketbookingsystem.repository.TrainRepository;
import org.project.trainticketbookingsystem.service.TrainService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;

    @Override
    public TrainDTO addTrain(TrainDTO train) {
        return null;
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        List<TrainEntity> trainEntities = trainRepository.findAll();
        return trainMapper.toTrainDTO(trainEntities);
    }
}
