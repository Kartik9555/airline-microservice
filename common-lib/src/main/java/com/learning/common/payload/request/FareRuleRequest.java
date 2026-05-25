package com.learning.common.payload.request;

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
public class FareRuleRequest {

    @NotBlank(message = "Rule name is required")
    private String ruleName;

    private Long airlineId;

    @NotNull(message = "Fare ID is required")
    private Long fareId;

    private Boolean isRefundable;

    @PositiveOrZero(message = "Change fee must be zero or positive")
    private Double changeFee;

    @PositiveOrZero(message = "Cancellation fee must be zero or positive")
    private Double cancellationFee;

    @PositiveOrZero(message = "Change deadline hours must be zero or positive")
    private Integer changeDeadlineHours;

    @PositiveOrZero(message = "Refund deadline days must be zero or positive")
    private Integer refundDeadlineDays;

    private Boolean isChangeable;
}
