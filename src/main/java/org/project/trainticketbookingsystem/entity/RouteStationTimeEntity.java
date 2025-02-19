package org.project.trainticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "stop_time")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteStationTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private RouteEntity route;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;

    private int stopOrder;

}
