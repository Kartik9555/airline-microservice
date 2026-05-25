package com.learning.pricing.service.model;

import com.learning.common.embeddable.BoardingBenefits;
import com.learning.common.embeddable.FlexibilityBenefits;
import com.learning.common.embeddable.InFlightBenefits;
import com.learning.common.embeddable.PremiumServiceBenefits;
import com.learning.common.embeddable.SeatBenefits;
import com.learning.common.enums.CabinClassType;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Character rbdCode;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long cabinClassId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CabinClassType cabinClass;

    @Column(nullable = false)
    private Double baseFare;

    private Double taxesAndFees;
    private Double airlineFees;

    @Column(nullable = false)
    private Double currentPrice;

    @Column(nullable = false)
    private String fareLabel;

//    private BaggagePolicy baggagePolicy;

    @OneToOne
    private FareRule fareRule;

    @Builder.Default
    @Embedded
    private SeatBenefits seatBenefits = SeatBenefits.builder().build();

    @Builder.Default
    @Embedded
    private BoardingBenefits boardingBenefits = BoardingBenefits.builder().build();

    @Builder.Default
    @Embedded
    private InFlightBenefits inFlightBenefits = InFlightBenefits.builder().build();

    @Builder.Default
    @Embedded
    private FlexibilityBenefits flexibilityBenefits = FlexibilityBenefits.builder().build();

    @Builder.Default
    @Embedded
    private PremiumServiceBenefits premiumServiceBenefits = PremiumServiceBenefits.builder().build();

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    public Double getTotalPrice() {
        double total = currentPrice;
        if (taxesAndFees != null) total += taxesAndFees;
        if (airlineFees != null) total += airlineFees;
        return total;
    }
}