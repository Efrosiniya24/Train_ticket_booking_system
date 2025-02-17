package org.project.trainticketbookingsystem.repository;

import org.project.trainticketbookingsystem.entity.CoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, Long> {
}
