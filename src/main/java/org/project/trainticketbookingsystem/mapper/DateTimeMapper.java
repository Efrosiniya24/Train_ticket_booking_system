package org.project.trainticketbookingsystem.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class DateTimeMapper {

    @Named("localDateTimeToLocalDate")
    public LocalDate localDateTimeToLocalDate(LocalDateTime localDateTime) {
        return (localDateTime != null) ? localDateTime.toLocalDate() : null;
    }
}
