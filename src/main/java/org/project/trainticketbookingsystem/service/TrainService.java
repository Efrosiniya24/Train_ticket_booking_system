package org.project.trainticketbookingsystem.service;

import java.util.List;
import org.project.trainticketbookingsystem.dto.TrainDto;

public interface TrainService {
    TrainDto addTrain(TrainDto train);

    List<TrainDto> getAllTrains();

    TrainDto getTrainById(Long id);

    int getNumberOfSeats(Long trainId);

    void deleteTrainById(Long id);

    TrainDto updateTrain(Long id, TrainDto trainDTO);
}
