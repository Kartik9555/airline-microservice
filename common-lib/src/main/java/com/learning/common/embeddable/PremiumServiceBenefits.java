package com.learning.common.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumServiceBenefits {

    @Builder.Default
    @Column(name = "lounge_access", nullable = false)
    private Boolean loungeAccess = false;

    @Builder.Default
    @Column(name = "airport_transfer", nullable = false)
    private Boolean airportTransfer = false;
}
