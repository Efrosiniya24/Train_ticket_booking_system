package org.project.trainticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "station")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<RouteStationTimeEntity> routeStationTime;
}
