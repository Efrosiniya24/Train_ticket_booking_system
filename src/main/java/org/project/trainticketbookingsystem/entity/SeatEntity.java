package org.project.trainticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "seat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private TrainEntity train;

    private double price;

    @ManyToMany(mappedBy = "seats")
    private List<BookingEntity> bookings;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private CoachEntity coach;
}
