package org.project.trainticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "coach")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private int numberOfSeats;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private TrainEntity train;

    @OneToMany(mappedBy = "coach")
    private List<SeatEntity> seats;
}
