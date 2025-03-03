package org.project.trainticketbookingsystem.repository;

import org.project.trainticketbookingsystem.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
    StationEntity findByName(String name);
    boolean existsByName(String name);
}
