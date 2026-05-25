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
public class FlexibilityBenefits {

    @Builder.Default
    @Column(name = "free_date_change", nullable = false)
    private Boolean freeDateChange = false;

    @Builder.Default
    @Column(name = "partial_refund", nullable = false)
    private Boolean partialRefund = false;

    @Builder.Default
    @Column(name = "full_refund", nullable = false)
    private Boolean fullRefund = false;
}
