package org.project.trainticketbookingsystem.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchTicketDto {
    private Long departureCityId;
    private Long arrivalCityId;
    private LocalDate departureDate;
}
