package org.project.trainticketbookingsystem.repository;

import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, Long> {
    Optional<TrainEntity> findById(Long id);
}
