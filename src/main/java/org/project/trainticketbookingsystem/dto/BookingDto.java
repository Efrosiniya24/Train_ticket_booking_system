package org.project.trainticketbookingsystem.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import org.project.trainticketbookingsystem.entity.SeatEntity;

@Data
public class BookingDto {
    private Long bookingId;
    private List<SeatEntity> seatsList;
    private Long trainId;
    private Long routeId;
    private StationDto departureStation;
    private StationDto arrivalStation;
    private LocalDate travelDate;
}
