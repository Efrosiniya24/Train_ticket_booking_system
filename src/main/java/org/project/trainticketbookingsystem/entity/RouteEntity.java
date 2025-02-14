package org.project.trainticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "rout")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<RouteStationTimeEntity> routeStationTime;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private TrainEntity train;
}
