package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.SeatDTO;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.SeatEntity;
import org.project.trainticketbookingsystem.mapper.SeatMapper;
import org.project.trainticketbookingsystem.repository.SeatRepository;
import org.project.trainticketbookingsystem.service.SeatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    @Override
    public List<SeatDTO> createSeat(CoachEntity coachEntity, List<SeatDTO> seatDTOs) {
        List<SeatEntity> seatEntities = seatDTOs.stream()
                .map(seatDTO -> SeatEntity.builder()
                        .price(seatDTO.getPrice())
                        .coach(coachEntity)
                        .build())
                .toList();

        seatRepository.saveAll(seatEntities);
        return seatMapper.toSeatDTO(seatEntities);
    }

    @Override
    public void deleteSeat(List<SeatEntity> seatEntity) {
        seatRepository.deleteAll(seatEntity);
    }
}
