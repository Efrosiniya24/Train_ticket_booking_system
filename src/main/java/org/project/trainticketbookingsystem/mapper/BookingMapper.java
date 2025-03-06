package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.BookingDto;
import org.project.trainticketbookingsystem.entity.BookingEntity;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDto toBookingDTO(BookingEntity bookingEntity);
}
