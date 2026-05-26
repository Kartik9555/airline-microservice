package com.learning.ancillary.service.model;

import com.learning.ancillary.service.service.impl.AncillaryMetadataConverter;
import com.learning.common.domain.AncillaryMetadata;
import com.learning.common.enums.AncillaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
