package org.project.trainticketbookingsystem.web;

import lombok.AllArgsConstructor;
import org.project.trainticketbookingsystem.dto.BookingDTO;
import org.project.trainticketbookingsystem.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/train")
public class BookingController {

    private final BookingService bookingService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/booking")
    private ResponseEntity<BookingDTO> bookingTicket(@RequestBody BookingDTO bookingDTO, @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("Received booking request: {}" + bookingDTO);

        if (bookingService == null) {
            System.out.println("BookingService is null in BookingController!");
            throw new IllegalStateException("BookingService is not injected properly!");
        }

        if (userDetails == null) {
            System.out.println("UserDetails is null!");
            throw new IllegalStateException("UserDetails is missing!");
        }

        System.out.println("Authenticated user: {}" + userDetails.getUsername());
        BookingDTO newBookingDTO = bookingService.bookTicket(bookingDTO, userDetails);
        return ResponseEntity.ok(newBookingDTO);
    }
}