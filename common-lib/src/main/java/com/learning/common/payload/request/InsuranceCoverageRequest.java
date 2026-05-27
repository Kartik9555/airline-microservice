package com.learning.common.payload.request;

import com.learning.common.enums.CoverageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceCoverageRequest {

    @NotNull(message = "Ancillary ID is required")
    private Long ancillaryId;

    @NotNull(message = "Coverage Type is required")
    private CoverageType coverageType;

    @NotBlank(message = "Coverage Name is required")
    @Size(max = 200, message = "Coverage Name must be at most 200 characters")
    private String name;

    @NotBlank(message = "Coverage Description is required")
    @Size(max = 1000, message = "Coverage Description must be at most 1000 characters")
    private String description;

    @NotNull(message = "Coverage Amount is required")
    @PositiveOrZero(message = "Coverage Amount must be zero or positive")
    private Double coverageAmount;

    private Boolean isFlat;

    @Size(max = 500, message = "Claim Condition must be at most 500 characters")
    private String claimCondition;

    @Size(max = 100, message = "Emergency Contact must be at most 100 characters")
    private String emergencyContact;

    private Integer displayOrder;
    private Boolean active;
}
