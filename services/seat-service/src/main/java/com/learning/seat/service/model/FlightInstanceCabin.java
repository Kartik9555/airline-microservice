package com.learning.seat.service.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "flight_instance_cabin")
public class FlightInstanceCabin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_instance_cabin_seq")
    @SequenceGenerator(name = "flight_instance_cabin_seq", sequenceName = "flight_instance_cabin_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Long flightInstanceId;

    @ManyToOne
    @JoinColumn(name = "cabin_class_id", nullable = false)
    private CabinClass cabinClass;

    @Column(nullable = false)
    private Integer totalSeats;

    private Integer bookedSeats;

    @Builder.Default
    @OneToMany(mappedBy = "flightInstanceCabin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatInstance> seats = new ArrayList<>();

    public Integer getAvailableSeats() {
        return totalSeats - bookedSeats;
    }
}
