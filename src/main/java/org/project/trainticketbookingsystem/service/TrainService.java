package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.TrainDTO;

import java.util.List;

public interface TrainService {
    TrainDTO addTrain(TrainDTO train);

    List<TrainDTO> getAllTrains();
    TrainDTO getTrainById(Long id);
}
