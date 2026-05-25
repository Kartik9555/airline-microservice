package com.learning.common.payload.response;

import com.learning.common.enums.CabinClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FareResponse {
    private Long id;
    private String name;
    private Character rbdCode;
    private Long flightId;
    private Long cabinClassId;
    private CabinClassType cabinClass;

    private Double baseFare;
    private Double taxesAndFees;
    private Double airlineFees;
    private Double currentPrice;
    private Double totalPrice;
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

    private Long fareRulesId;
    // private FareRulesResponse fareRules;
    // private BaggagePolicyResponse baggagePolicy;

    private Instant createdAt;
    private Instant updatedAt;

}
