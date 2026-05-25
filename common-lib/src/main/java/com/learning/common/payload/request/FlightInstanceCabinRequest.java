package com.learning.common.payload.request;

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
public class FlightInstanceCabinRequest {

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "Flight Instance ID is required")
    private Long flightInstanceId;

    @NotNull(message = "Cabin Class ID is required")
    private Long cabinClassId;

    @NotNull(message = "Base fare is required")
    @Positive(message = "Base fare must be greater than zero")
    private Double baseFare;

    @NotNull(message = "Window surcharge is required")
    private Double windowSurcharge;

    @NotNull(message = "Aisle surcharge is required")
    private Double aisleSurcharge;

    @NotNull(message = "Taxes and fees are required")
    @PositiveOrZero(message = "Taxes and fees must be zero or positive")
    private Double taxesAndFees;

    @NotNull(message = "Airline fees are required")
    @PositiveOrZero(message = "Airline fees must be zero or positive")
    private Double airlineFees;

    private Double currentPrice;
    private Boolean isActive;
}
