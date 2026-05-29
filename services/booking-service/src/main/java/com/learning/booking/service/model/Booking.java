package com.learning.booking.service.model;

import com.learning.common.embeddable.ContactInfo;
import com.learning.common.enums.BookingStatus;
import com.learning.common.enums.CabinClassType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bookingReference;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long flightId;

    @Column(nullable = false)
    private Long flightInstanceId;

    @Column(nullable = false)
    private Long airlineId;

    @Enumerated(EnumType.STRING)
    private CabinClassType cabinClassType = CabinClassType.ECONOMY;

    @Column(nullable = false)
    private Long fareId;

    @Builder.Default
    private Boolean flexibleTicket = false;

    private LocalDateTime ticketTimeLimit;

    @Builder.Default
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Passenger> passengers = new HashSet<>();

    @ElementCollection
    private List<Long> seatInstanceIds;

    @ElementCollection
    private List<Long> ancillaryIds;

    @ElementCollection
    private List<Long> mealIds;

    @Builder.Default
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> tickets = new HashSet<>();

    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private Boolean ticketIssued;

    @Embedded
    private ContactInfo contactInfo;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant bookingDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant lastModified;
}
