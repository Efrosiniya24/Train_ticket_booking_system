package org.project.trainticketbookingsystem.repository;

import java.time.LocalDate;
import java.util.List;
import org.project.trainticketbookingsystem.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    @Query("SELECT b FROM BookingEntity b WHERE b.train.id = :trainId AND b.route.id = :routeId AND FUNCTION('DATE', b.arrivalDateTime) = :arrivalDate")
    List<BookingEntity> findByParam(@Param("trainId") Long trainId, @Param("routeId") Long routeId, @Param("arrivalDate") LocalDate arrivalDate);

    List<BookingEntity> findAllByUserId(Long userId);
}

