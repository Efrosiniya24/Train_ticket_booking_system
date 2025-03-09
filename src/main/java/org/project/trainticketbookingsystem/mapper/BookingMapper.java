package org.project.trainticketbookingsystem.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.trainticketbookingsystem.dto.BookingDto;
import org.project.trainticketbookingsystem.dto.BookingRequestDto;
import org.project.trainticketbookingsystem.entity.BookingEntity;

@Mapper(componentModel = "spring", uses = {TrainMapper.class, RouteMapper.class, SeatMapper.class, DateTimeMapper.class})
public interface BookingMapper {
    @Mapping(source = "id", target = "bookingId")
    @Mapping(source = "seats", target = "seatsList")
    @Mapping(source = "train.id", target = "trainId")
    @Mapping(source = "route.id", target = "routeId")
    @Mapping(source = "departureStation", target = "departureStation")
    @Mapping(source = "arrivalStation", target = "arrivalStation")
    @Mapping(source = "arrivalDateTime", target = "travelDate", qualifiedByName = "localDateTimeToLocalDate")
    BookingDto toBookingDTO(BookingEntity bookingEntity);

    List<BookingDto> toBookingDTO(List<BookingEntity> bookingEntities);


    @Mapping(source = "id", target = "bookingId")
    @Mapping(source = "seats", target = "seatsList")
    @Mapping(source = "train", target = "train")
    @Mapping(source = "route", target = "route")
    @Mapping(source = "departureStation", target = "departureStation")
    @Mapping(source = "arrivalStation", target = "arrivalStation")
    @Mapping(source = "arrivalDateTime", target = "travelDate", qualifiedByName = "localDateTimeToLocalDate")
    @Mapping(source = "departureDateTime", target = "departureDataTime")
    @Mapping(source = "arrivalDateTime", target = "arrivalDataTime")
    BookingRequestDto toBookingRequestDTO(BookingEntity bookingEntity);

    List<BookingRequestDto> toBookingRequestDTO(List<BookingEntity> bookingEntities);
}
