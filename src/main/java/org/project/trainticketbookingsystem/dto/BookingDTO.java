package org.project.trainticketbookingsystem.dto;

import lombok.Data;
import org.project.trainticketbookingsystem.entity.SeatEntity;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookingDTO {
    private Long bookingId;
    private List<SeatEntity> seatsList;
    private Long trainId;
    private Long routeId;
    private StationDTO departureStation;
    private StationDTO arrivalStation;
    private LocalDate travelDate;
}
