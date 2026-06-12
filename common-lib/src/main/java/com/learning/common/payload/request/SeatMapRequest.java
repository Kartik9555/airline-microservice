package com.learning.common.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatMapRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Total rows is required")
    @Positive(message = "Total rows must be a positive number")
    private Integer totalRows;

    @NotNull(message = "Left seats per rows is required")
    @Positive(message = "Left seats per rows must be a positive number")
    private Integer leftSeatsPerRow;

    @NotNull(message = "Right seats per rows is required")
    @Positive(message = "Right seats per rows must be a positive number")
    private Integer rightSeatsPerRow;
    private Long cabinClassId;

    @NotNull(message = "Base Price per seat is required")
    @Positive(message = "Base Price per seat must be a positive number")
    private Double basePrice;

    @PositiveOrZero(message = "Premium Surcharge per seat must be a positive number or zero")
    private Double premiumSurcharge;
}
