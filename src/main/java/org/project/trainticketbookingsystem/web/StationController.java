package org.project.trainticketbookingsystem.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.StationDto;
import org.project.trainticketbookingsystem.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/station")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;

    @GetMapping("/allStations")
    public ResponseEntity<List<StationDto>> getAllStations() {
        List<StationDto> stations = stationService.getAllStations();
        return ResponseEntity.ok(stations);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<StationDto> addStation(@RequestBody StationDto stationDTO) {
        StationDto savedStationDto = stationService.addStation(stationDTO);
        return ResponseEntity.ok(savedStationDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return ResponseEntity.ok("Station is deleted successfully");
    }
}
