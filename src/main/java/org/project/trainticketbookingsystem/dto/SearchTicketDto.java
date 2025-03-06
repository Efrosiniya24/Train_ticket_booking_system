package org.project.trainticketbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchTicketDto {
    private Long departureCityId;
    private Long arrivalCityId;
    private LocalDate departureDate;
}
