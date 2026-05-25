package com.learning.common.payload.request;

import com.learning.common.embeddable.Baggage;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaggagePolicyRequest {

    @NotBlank(message = "Baggage Policy Name is required")
    private String name;

    @NotNull(message = "Fare ID is required")
    private Long fareId;

    private String description;

    @Embedded
    private Baggage cabinBaggage;

    @Embedded
    private Baggage checkinBaggage;

    @PositiveOrZero(message = "Free Checked Bags Allowance must be zero or positive")
    private Integer freeCheckedBagsAllowance;

    private Boolean priorityBaggage;
    private Boolean extraBaggageAllowance;
}
