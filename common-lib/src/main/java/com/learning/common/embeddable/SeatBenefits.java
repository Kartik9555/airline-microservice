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
public class SeatBenefits {

    @Builder.Default
    @Column(name = "extra_seat_space", nullable = false)
    private Boolean extraSeatSpace = false;

    @Builder.Default
    @Column(name = "preferred_seat_choice", nullable = false)
    private Boolean preferredSeatChoice = false;

    @Builder.Default
    @Column(name = "advance_seat_selection", nullable = false)
    private Boolean advanceSeatSelection = false;

    @Builder.Default
    @Column(name = "guaranteed_seat_together", nullable = false)
    private Boolean guaranteedSeatTogether = false;
}
