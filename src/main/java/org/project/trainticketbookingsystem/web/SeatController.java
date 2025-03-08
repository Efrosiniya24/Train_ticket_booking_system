package org.project.trainticketbookingsystem.web;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.GetSeatStatusDto;
import org.project.trainticketbookingsystem.dto.SeatStatusDtoResponse;
import org.project.trainticketbookingsystem.service.BookingSeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/train/seats")
@RequiredArgsConstructor
public class SeatController {
    private final BookingSeatService bookingSeatService;

    @GetMapping("/seatWithStatus")
    public ResponseEntity<SeatStatusDtoResponse> getSeatsWithStatus(@RequestBody GetSeatStatusDto statusDto) {
        return ResponseEntity.ok(bookingSeatService.getSeatsWithStatusForSegment(statusDto));
    }
}