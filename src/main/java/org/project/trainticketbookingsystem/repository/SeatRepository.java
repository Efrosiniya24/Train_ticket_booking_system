package org.project.trainticketbookingsystem.repository;

import java.util.List;
import org.project.trainticketbookingsystem.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    @Query("SELECT s.price FROM SeatEntity s WHERE s.coach.train.id = :trainId ORDER BY s.id LIMIT 1")
    double findSinglePriceByTrainId(@Param("trainId") Long trainId);

    List<SeatEntity> findByCoachIdIn(List<Long> coachIds);

}
