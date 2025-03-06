package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.TrainDto;
import org.project.trainticketbookingsystem.entity.TrainEntity;

import java.util.List;

public interface TrainService {
    TrainDto addTrain(TrainDto train);
    List<TrainDto> getAllTrains();
    TrainDto getTrainById(Long id);
    int getNumberOfSeats(Long trainId);
    TrainEntity getTrainEntityById(Long id);
    void deleteTrainById(Long id);
    TrainDto updateTrain(Long id, TrainDto trainDTO);
}
