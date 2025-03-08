package org.project.trainticketbookingsystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.SeatDto;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.SeatEntity;
import org.project.trainticketbookingsystem.exceptions.SeatException;
import org.project.trainticketbookingsystem.mapper.SeatMapper;
import org.project.trainticketbookingsystem.repository.SeatRepository;
import org.project.trainticketbookingsystem.service.SeatService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    @Override
    public List<SeatDto> createSeat(CoachEntity coachEntity, List<SeatDto> seatDtos) {
        List<SeatEntity> seatEntities = seatDtos.stream()
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

    @Override
    public List<SeatEntity> updateSeats(List<SeatDto> seats) {
        List<SeatEntity> seatEntities = seats.stream()
                .map(seatDTO -> {
                    SeatEntity seatEntity = seatRepository.findById(seatDTO.getId()).orElseThrow(() -> new SeatException("Seat not found"));
                    seatEntity.setPrice(seatDTO.getPrice());
                    seatEntity.setId(seatDTO.getId());
                    return seatEntity;
                })
                .collect(Collectors.toList());

        return seatRepository.saveAll(seatEntities);
    }

    @Override
    public double getPrice(Long trainId) {
        return seatRepository.findSinglePriceByTrainId(trainId);
    }

    @Override
    public SeatEntity getSeatById(Long seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatException("Seat not found"));
    }
}
