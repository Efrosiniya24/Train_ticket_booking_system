package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.TrainDTO;
import org.project.trainticketbookingsystem.entity.TrainEntity;

import java.util.List;

public interface TrainService {
    TrainDTO addTrain(TrainDTO train);
    List<TrainDTO> getAllTrains();
    TrainDTO getTrainById(Long id);
    int getNumberOfSeats(Long trainId);
    TrainEntity getTrainEntityById(Long id);
    void deleteTrainById(Long id);
}
