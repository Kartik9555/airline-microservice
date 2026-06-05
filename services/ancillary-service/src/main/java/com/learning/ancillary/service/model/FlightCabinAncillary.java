package com.learning.ancillary.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "flight_cabin_ancillary")
public class FlightCabinAncillary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_cabin_ancillary_seq")
    @SequenceGenerator(name = "flight_cabin_ancillary_seq", sequenceName = "flight_cabin_ancillary_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long cabinClassId;

    @ManyToOne
    @JoinColumn(name = "ancillary_id")
    private Ancillary ancillary;

    private Boolean available;

    private Integer maxQuantity;
    private Double price;

    @Builder.Default
    private Boolean includedInFare = false;
}
