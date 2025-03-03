package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.BookingDTO;
import org.project.trainticketbookingsystem.entity.BookingEntity;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDTO toBookingDTO(BookingEntity bookingEntity);
}
