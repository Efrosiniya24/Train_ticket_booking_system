package org.project.trainticketbookingsystem.service;

import java.util.List;
import org.project.trainticketbookingsystem.dto.BookingDto;
import org.project.trainticketbookingsystem.dto.BookingRequestDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface BookingService {
    BookingDto bookTicket(BookingDto bookingDTO, UserDetails user);

    List<BookingRequestDto> getBookingForCurrentUser(UserDetails userDetails);
}
