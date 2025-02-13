package org.project.trainticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rout")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<RouteStationTimeEntity> routeStationTime;

    @ManyToMany
    @JoinTable(
            name = "rout_train",
            joinColumns = @JoinColumn(name = "rout_id"),
            inverseJoinColumns = @JoinColumn(name = "train_id")
    )
    private List<TrainEntity> trains;
}
