package org.project.trainticketbookingsystem.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Min(1)
    private int numberOfCoaches;

    @OneToMany(mappedBy = "train")
    private List<RouteEntity> routes;

    @OneToMany(mappedBy = "train")
    private List<BookingEntity> bookingEntity;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CoachEntity> coachEntities;
}
