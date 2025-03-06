package org.project.trainticketbookingsystem.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.trainticketbookingsystem.dto.BookingDto;
import org.project.trainticketbookingsystem.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/train")
public class BookingController {

    private final BookingService bookingService;

//    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/booking")
    private ResponseEntity<BookingDto> bookingTicket(@RequestBody BookingDto bookingDTO, @AuthenticationPrincipal UserDetails userDetails) {

        log.info("Received booking request: {}", bookingDTO);

        if (userDetails == null) {
           log.info("UserDetails is null!");
            throw new IllegalStateException("UserDetails is missing!");
        }

        log.info("Authenticated user: {}", userDetails.getUsername());
        BookingDto newBookingDto = bookingService.bookTicket(bookingDTO, userDetails);
        return ResponseEntity.ok(newBookingDto);
    }
}