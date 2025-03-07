package org.project.trainticketbookingsystem.repository;

import java.util.List;
import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, Long> {
    List<CoachEntity> findByTrain_Id(Long trainId);
}
