package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.BookingDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface BookingService {
    BookingDto bookTicket(BookingDto bookingDTO, UserDetails user);
}
