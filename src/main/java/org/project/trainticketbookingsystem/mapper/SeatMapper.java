package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.SeatDto;
import org.project.trainticketbookingsystem.entity.SeatEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    SeatDto toSeatDTO(SeatEntity seatEntity);
    List<SeatDto> toSeatDTO(List<SeatEntity> seatEntities);
}
