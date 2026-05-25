package com.learning.common.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FareRequest {
    @NotBlank(message = "Fare name is required")
    private String name;

    @NotBlank(message = "Fare code is required")
    private Character rbdCode;

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "Cabin Class ID is required")
    private Long cabinClassId;

    @NotNull(message = "Base fare is required")
    @Positive(message = "Base fare must be a positive number")
    private Double baseFare;

    private Double taxesAndFees;
    private Double airlineFees;
    private Double currentPrice;

    @Size(max = 100, message = "Fare label must be at most 100 characters")
    private String fareLabel;

    private Boolean extraSeatSpace;
    private Boolean preferredSeatChoice;
    private Boolean advanceSeatSelection;
    private Boolean guaranteedSeatTogether;

    private Boolean priorityBoarding;
    private Boolean priorityCheckin;
    private Boolean fastTrackSecurity;

    private Boolean complimentaryMeals;
    private Boolean premiumMealChoice;
    private Boolean inFlightInternet;
    private Boolean inFlightEntertainment;
    private Boolean complimentaryBeverages;

    private Boolean freeDateChange;
    private Boolean partialRefund;
    private Boolean fullRefund;

    private Boolean loungeAccess;
    private Boolean airportTransfer;
}
