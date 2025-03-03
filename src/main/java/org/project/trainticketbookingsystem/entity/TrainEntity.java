package org.project.trainticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "train")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String train;

    @OneToMany(mappedBy = "train")
    private List<RouteEntity> routes;

    @OneToMany(mappedBy = "train")
    private List<BookingEntity> bookingEntity;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CoachEntity> coachEntities;
}
