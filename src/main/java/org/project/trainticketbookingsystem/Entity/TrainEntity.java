package org.project.trainticketbookingsystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "train")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "trains")
    private List<RouteEntity> routes;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    private List<SeatEntity> seats;

    private int numberOfSeats;
}
