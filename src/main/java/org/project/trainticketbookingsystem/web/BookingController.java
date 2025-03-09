package org.project.trainticketbookingsystem.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.trainticketbookingsystem.dto.BookingDto;
import org.project.trainticketbookingsystem.dto.BookingRequestDto;
import org.project.trainticketbookingsystem.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/booking")
    public ResponseEntity<BookingDto> bookingTicket(@RequestBody BookingDto bookingDTO, @AuthenticationPrincipal UserDetails userDetails) {

        BookingDto newBookingDto = bookingService.bookTicket(bookingDTO, userDetails);
        return ResponseEntity.ok(newBookingDto);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getBooking")
    public ResponseEntity<List<BookingRequestDto>> getBookingForCurrentUSer(@AuthenticationPrincipal UserDetails userDetails) {
        List<BookingRequestDto> bookingDtos = bookingService.getBookingForCurrentUser(userDetails);
        return ResponseEntity.ok(bookingDtos);
    }
}