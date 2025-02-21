package org.project.trainticketbookingsystem.web;

import lombok.AllArgsConstructor;
import org.project.trainticketbookingsystem.dto.TrainDTO;
import org.project.trainticketbookingsystem.service.TrainService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/train")
@AllArgsConstructor
public class TrainController {
    private final TrainService trainService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addTrain")
    private ResponseEntity<TrainDTO> addTrain(@RequestBody TrainDTO trainDTO) {
        TrainDTO train = trainService.addTrain(trainDTO);
        return ResponseEntity.ok(train);
    }

    @GetMapping("/allTrains")
    private ResponseEntity<List<TrainDTO>> getAllTrains() {
        List<TrainDTO> trainDTO = trainService.getAllTrains();
        return ResponseEntity.ok(trainDTO);
    }
}
