package org.project.trainticketbookingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.BookingDTO;
import org.project.trainticketbookingsystem.dto.SeatDTO;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.project.trainticketbookingsystem.entity.SeatEntity;
import org.project.trainticketbookingsystem.mapper.SeatMapper;
import org.project.trainticketbookingsystem.repository.SeatRepository;
import org.project.trainticketbookingsystem.service.SeatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<SeatEntity> getSeatEntityFromBooking(BookingDTO bookingDTO) {
        return bookingDTO.getSeatsList().stream()
                .map(currentSeat -> seatRepository.findById(currentSeat.getId()).orElseThrow(() -> new RuntimeException("Seat not found")))
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getSeatsId(List<SeatEntity> seatEntities) {
        return seatEntities.stream()
                .map(SeatEntity::getId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSeat(List<SeatEntity> seatEntity) {
        seatRepository.deleteAll(seatEntity);
    }

    @Override
    public List<SeatEntity> updateSeats(List<SeatEntity> seats) {
        List<SeatEntity> seatEntities = seats.stream()
                .peek(seatEntity-> {
                        SeatEntity seatEntity1 = seatRepository.findById(seatEntity.getId()).orElseThrow(() -> new RuntimeException("Seat not found"));
                        seatEntity1.setPrice(seatEntity.getPrice());
                        seatEntity1.setCoach(seatEntity.getCoach());
                })
                .collect(Collectors.toList());

        return seatRepository.saveAll(seatEntities);
    }
}
