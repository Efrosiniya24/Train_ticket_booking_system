package org.project.trainticketbookingsystem.service;

import java.util.List;
import java.util.Map;
import org.project.trainticketbookingsystem.dto.BookingDto;
import org.project.trainticketbookingsystem.dto.GetSeatStatusDto;
import org.project.trainticketbookingsystem.dto.RouteStationTimeDto;
import org.project.trainticketbookingsystem.dto.SeatDto;
import org.project.trainticketbookingsystem.dto.SeatStatusDtoResponse;

public interface BookingSeatService {
    List<SeatDto> getSeatBooking(BookingDto bookingDto, Map<String, RouteStationTimeDto> stations);

    SeatStatusDtoResponse getSeatsWithStatusForSegment(GetSeatStatusDto statusDto);
}
