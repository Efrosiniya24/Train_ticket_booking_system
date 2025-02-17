package org.project.trainticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "seat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;

    @ManyToMany(mappedBy = "seats")
    private List<BookingEntity> bookings;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private CoachEntity coach;
}
