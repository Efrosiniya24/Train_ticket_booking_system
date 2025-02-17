package org.project.trainticketbookingsystem.mapper;

import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.SeatDTO;
import org.project.trainticketbookingsystem.entity.SeatEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    List<SeatDTO> toSeatDTO(List<SeatEntity> seatEntities);
}
