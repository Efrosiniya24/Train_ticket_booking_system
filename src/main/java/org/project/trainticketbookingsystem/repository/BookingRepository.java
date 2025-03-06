package org.project.trainticketbookingsystem.repository;

import java.time.LocalDate;
import java.util.List;
import org.project.trainticketbookingsystem.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByTrainIdAndRouteIdAndArrivalDate(Long trainId, Long routeId, LocalDate arrivalDate);
}

