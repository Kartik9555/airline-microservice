package com.learning.airline.core.service.model;

import com.learning.common.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "aircraft")
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aircraft_seq")
    @SequenceGenerator(name = "aircraft_seq", sequenceName = "aircraft_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private Integer seatingCapacity;

    @Builder.Default
    @Column(name = "economy_seats")
    private Integer economySeats = 0;

    @Builder.Default
    @Column(name = "premium_economy_seats")
    private Integer premiumEconomySeats = 0;

    @Builder.Default
    @Column(name = "business_seats")
    private Integer businessSeats = 0;

    @Builder.Default
    @Column(name = "first_class_seats")
    private Integer firstClassSeats = 0;

    @Column(name = "range_km")
    private Integer rangeKm;

    @Column(name = "cruising_speed_kmh")
    private Integer cruisingSpeedKmh;

    @Column(name = "max_altitude_ft")
    private Integer maxAltitudeFt;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AircraftStatus status = AircraftStatus.ACTIVE;

    @Builder.Default
    private Boolean isAvailable = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id")
    private Airline airline;

    private Long currentAirportId;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    public Integer getTotalSeats() {
        return economySeats + firstClassSeats + premiumEconomySeats + businessSeats;
    }

    public boolean isOperational() {
        return AircraftStatus.ACTIVE.equals(status) && Boolean.TRUE.equals(isAvailable);
    }

    public boolean requireMaintenance() {
        return nextMaintenanceDate != null && nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }
}