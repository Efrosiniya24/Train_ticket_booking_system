package org.project.trainticketbookingsystem.repository;

import java.util.List;
import java.util.Optional;
import org.project.trainticketbookingsystem.entity.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, Long> {
    Optional<TrainEntity> findById(Long id);

    List<TrainEntity> findAll();

    boolean existsByTrain(String name);
}
