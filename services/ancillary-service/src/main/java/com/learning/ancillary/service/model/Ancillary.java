package com.learning.ancillary.service.model;

import com.learning.ancillary.service.service.impl.AncillaryMetadataConverter;
import com.learning.common.domain.AncillaryMetadata;
import com.learning.common.enums.AncillaryType;
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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Ancillary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AncillaryType type;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    private String subType;

    private String rfisc;

    @Column(nullable = false)
    private String name;

    private String description;

    @Convert(converter = AncillaryMetadataConverter.class)
    private AncillaryMetadata metadata;

    private Integer displayOrder;

    private Long airlineId;

    @OneToMany(mappedBy = "ancillary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InsuranceCoverage> coverages;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
