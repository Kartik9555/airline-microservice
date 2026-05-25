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
public class BoardingBenefits {

    @Builder.Default
    @Column(name = "priority_boarding", nullable = false)
    private Boolean priorityBoarding = false;

    @Builder.Default
    @Column(name = "priority_checkin", nullable = false)
    private Boolean priorityCheckin = false;

    @Builder.Default
    @Column(name = "fast_track_security", nullable = false)
    private Boolean fastTrackSecurity = false;
}
