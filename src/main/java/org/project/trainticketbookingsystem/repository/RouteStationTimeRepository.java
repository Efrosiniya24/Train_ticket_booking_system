package org.project.trainticketbookingsystem.repository;

import java.util.List;
import org.project.trainticketbookingsystem.entity.RouteStationTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteStationTimeRepository extends JpaRepository<RouteStationTimeEntity, Long> {
    @Query("SELECT rst FROM RouteStationTimeEntity rst WHERE (rst.route.id = :routeId AND rst.station.id = :departureStationId) OR (rst.route.id = :routeId AND rst.station.id = :arrivalStationId)")
    List<RouteStationTimeEntity> findStationsByRouteAndDepartureArrival(
            @Param("routeId") Long routeId,
            @Param("departureStationId") Long departureStationId,
            @Param("arrivalStationId") Long arrivalStationId);

}
