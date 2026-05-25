package com.learning.seat.service.model;

import com.learning.common.enums.SeatType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private Integer seatRow;

    private Character columnLetter;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private Double basePrice;
    private Double premiumSurcharge;

    @Builder.Default
    private Boolean isAvailable = true;

    @Builder.Default
    private Boolean isBlocked = false;

    @Builder.Default
    private Boolean isEmergencyExit = false;

    @Builder.Default
    private Boolean isActive = true;

    @Builder.Default
    private Boolean hasExtraLegRoom = false;

    @Builder.Default
    private Boolean hasPowerOutlet = false;

    @Builder.Default
    private Boolean hasTvScreen = false;

    @Builder.Default
    private Boolean hasBassinet = false;

    @Builder.Default
    private Boolean isNearLavatory = false;

    @Builder.Default
    private Boolean isNearGalley = false;

    @Builder.Default
    private Boolean isWheelChairAccessible = false;

    @Builder.Default
    private Boolean hasExtraWidth = false;

    @Builder.Default
    private Boolean isPremiumSeat = false;

    private Integer seatPitch;
    private Integer seatWidth;
    private Integer reclineAngle;

    @ManyToOne
    private SeatMap seatMap;

    @ManyToOne
    private CabinClass cabinClass;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @Version
    private Long version;

    public Double getTotalPrice() {
        Double price = basePrice != null ? basePrice : Double.valueOf(0);
        if(premiumSurcharge != null) {
            price += premiumSurcharge;
        }
        return price;
    }

    public boolean isBookable() {
        return isActive && isAvailable && !isBlocked;
    }

    public String getFullPosition() {
        return seatRow + "" + columnLetter;
    }

}
