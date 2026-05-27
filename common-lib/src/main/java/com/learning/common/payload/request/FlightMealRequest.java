package com.learning.common.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightMealRequest {

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "Meal ID is required")
    private Long mealId;

    @Positive(message = "Price must be positive")
    private Double price;
    private Boolean available;

    @Positive(message = "Display Order must be positive")
    private Integer displayOrder;

    private String notes;
}
