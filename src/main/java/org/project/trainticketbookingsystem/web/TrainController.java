package org.project.trainticketbookingsystem.web;

import lombok.AllArgsConstructor;
import org.project.trainticketbookingsystem.dto.TrainDTO;
import org.project.trainticketbookingsystem.service.TrainService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/train")
@AllArgsConstructor
public class TrainController {
    private final TrainService trainService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addTrain")
    public ResponseEntity<TrainDTO> addTrain(@RequestBody TrainDTO trainDTO) {
        TrainDTO train = trainService.addTrain(trainDTO);
        return ResponseEntity.ok(train);
    }

    @GetMapping("/allTrains")
    public ResponseEntity<List<TrainDTO>> getAllTrains() {
        List<TrainDTO> trainDTO = trainService.getAllTrains();
        return ResponseEntity.ok(trainDTO);
    }

    @GetMapping("/numberOfSeats/{id}")
    public ResponseEntity<Integer> getNumberOfSeats(@PathVariable Long id) {
        int numberOfSeats = trainService.getNumberOfSeats(id);
        return ResponseEntity.ok(numberOfSeats);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id) {
        trainService.deleteTrainById(id);
        return ResponseEntity.ok().body(null);
    }

}
