package com.learning.pricing.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "baggage_policy")
public class BaggagePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "baggage_policy_seq")
    @SequenceGenerator(name = "baggage_policy_seq", sequenceName = "baggage_policy_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "fare_id", nullable = false)
    private Fare fare;

    @Column(nullable = false)
    private String name;

    private String description;

    private Double cabinBaggageMaxWeight;

    @Builder.Default
    private Integer cabinBaggagePieces = 1;

    private Double cabinBaggageWeightPerPiece;

    private Integer cabinBaggageMaxDimension;

    private Double checkinBaggageMaxWeight;

    @Builder.Default
    private Integer checkinBaggagePieces = 1;

    private Double checkinBaggageWeightPerPiece;

    @Builder.Default
    private Integer freeCheckedBagsAllowance = 0;

    @Builder.Default
    private Boolean priorityBaggage = false;

    @Builder.Default
    private Boolean extraBaggageAllowance = false;

    private Long airlineId;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
