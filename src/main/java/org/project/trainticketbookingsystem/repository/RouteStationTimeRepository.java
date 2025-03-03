package org.project.trainticketbookingsystem.repository;

import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteStationTimeRepository extends JpaRepository<RouteStationTimeEntity, Long> {
    RouteStationTimeEntity findByIdAndStation_Name(Long id, String station_name);
}
