package org.project.trainticketbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchTicketDTO {
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
}
