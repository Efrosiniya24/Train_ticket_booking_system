package org.project.trainticketbookingsystem.repository;

import org.project.trainticketbookingsystem.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    @Query("SELECT sb FROM BookingEntity sb " +
            "JOIN sb.seats s " +
            "JOIN sb.route.routeStationTime rst " +
            "JOIN rst.station st " +
            "WHERE s.id IN :seatIds " +
            "AND sb.train.id = :trainId " +
            "AND sb.arrivalDate = :travelDate " +
            "AND (rst.stopOrder < :arrivalStopOrder AND rst.stopOrder > :departureStopOrder)")
    List<BookingEntity> findConflictingBookings(
            @Param("seatIds") List<Long> seatIds,
            @Param("trainId") Long trainId,
            @Param("travelDate") LocalDate travelDate,
            @Param("departureStopOrder") int departureStopOrder,
            @Param("arrivalStopOrder") int arrivalStopOrder
    );
}

