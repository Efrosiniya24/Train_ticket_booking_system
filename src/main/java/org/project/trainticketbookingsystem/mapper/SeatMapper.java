package org.project.trainticketbookingsystem.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.project.trainticketbookingsystem.dto.SeatDto;
import org.project.trainticketbookingsystem.entity.SeatEntity;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    SeatDto toSeatDTO(SeatEntity seatEntity);

    List<SeatDto> toSeatDTO(List<SeatEntity> seatEntities);

    List<SeatEntity> toSeatEntity(List<SeatDto> seatDtos);
}
