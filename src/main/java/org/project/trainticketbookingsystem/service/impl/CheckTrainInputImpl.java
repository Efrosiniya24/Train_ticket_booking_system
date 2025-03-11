package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.exceptions.TrainException;
import org.project.trainticketbookingsystem.service.CheckTrainInput;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckTrainInputImpl implements CheckTrainInput {
    @Override
    public boolean checkInput(int numberOfCoaches) {
        if (numberOfCoaches < 1) {
            throw new TrainException("Number of coaches should be > 0");
        }
        return true;
    }
}
