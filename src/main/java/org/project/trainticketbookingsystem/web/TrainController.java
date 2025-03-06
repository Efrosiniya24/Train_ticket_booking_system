package org.project.trainticketbookingsystem.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.TrainDto;
import org.project.trainticketbookingsystem.service.TrainService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/train")
@RequiredArgsConstructor
public class TrainController {
    private final TrainService trainService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addTrain")
    public ResponseEntity<TrainDto> addTrain(@RequestBody TrainDto trainDTO) {
        TrainDto train = trainService.addTrain(trainDTO);
        return ResponseEntity.ok(train);
    }

    @GetMapping("/allTrains")
    public ResponseEntity<List<TrainDto>> getAllTrains() {
        List<TrainDto> trainDTO = trainService.getAllTrains();
        return ResponseEntity.ok(trainDTO);
    }

    @GetMapping("/numberOfSeats/{id}")
    public ResponseEntity<Integer> getNumberOfSeats(@PathVariable Long id) {
        int numberOfSeats;
        numberOfSeats = trainService.getNumberOfSeats(id);
        return ResponseEntity.ok(numberOfSeats);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id) {
        trainService.deleteTrainById(id);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<TrainDto> updateTrain(@PathVariable Long id, @RequestBody TrainDto trainDTO) {
        TrainDto updatedTrain = trainService.updateTrain(id, trainDTO);
        return ResponseEntity.ok(updatedTrain);
    }
}
