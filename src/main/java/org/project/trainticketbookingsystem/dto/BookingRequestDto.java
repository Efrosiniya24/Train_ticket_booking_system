package org.project.trainticketbookingsystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class BookingRequestDto {
    private Long bookingId;
    private List<SeatDto> seatsList;
    private TrainDto train;
    private RouteDto route;
    private StationDto departureStation;
    private StationDto arrivalStation;
    private LocalDate travelDate;
    private LocalDateTime departureDataTime;
    private LocalDateTime arrivalDataTime;
}
