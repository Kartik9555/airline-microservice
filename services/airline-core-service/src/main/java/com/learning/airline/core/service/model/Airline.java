package com.learning.airline.core.service.model;

import com.learning.common.embeddable.Support;
import com.learning.common.enums.AirlineStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

import static com.learning.common.enums.AirlineStatus.ACTIVE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "airline")
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airline_seq")
    @SequenceGenerator(name = "airline_seq", sequenceName = "airline_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String iataCode;

    @Column(unique = true, nullable = false)
    private String icaoCode;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String name;

    private String alias;

    private String logoUrl;

    private String website;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private AirlineStatus status = ACTIVE;

    private String alliance;

    private Long headquarterCityId;

    private Long updatedById;

    @Embedded
    private Support support;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
