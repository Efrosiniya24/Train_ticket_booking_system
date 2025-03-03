package org.project.trainticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "booking_seat",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<SeatEntity> seats;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private TrainEntity train;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private RouteEntity route;

    @ManyToOne
    @JoinColumn(name = "departure_station_id")
    private StationEntity departureStation;

    @ManyToOne
    @JoinColumn(name = "arrival_station_id")
    private StationEntity arrivalStation;

    private LocalDate arrivalDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
