package org.project.trainticketbookingsystem.service;

import org.project.trainticketbookingsystem.dto.BookingDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface BookingService {
    BookingDTO bookTicket(BookingDTO bookingDTO, UserDetails user);
}
