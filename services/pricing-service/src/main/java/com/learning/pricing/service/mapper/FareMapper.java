package com.learning.pricing.service.mapper;

import com.learning.common.embeddable.BoardingBenefits;
import com.learning.common.embeddable.FlexibilityBenefits;
import com.learning.common.embeddable.InFlightBenefits;
import com.learning.common.embeddable.PremiumServiceBenefits;
import com.learning.common.embeddable.SeatBenefits;
import com.learning.common.payload.request.FareRequest;
import com.learning.common.payload.response.FareResponse;
import com.learning.pricing.service.model.Fare;

public class FareMapper {
    public static FareResponse toFare(Fare fare) {
        if (fare == null) return null;

        return FareResponse.builder()
                .id(fare.getId())
                .name(fare.getName())
                .rbdCode(fare.getRbdCode())
                .flightId(fare.getFlightId())
                .cabinClassId(fare.getCabinClassId())
                .cabinClass(fare.getCabinClass())
                .baseFare(fare.getBaseFare())
                .taxesAndFees(fare.getTaxesAndFees())
                .airlineFees(fare.getAirlineFees())
                .currentPrice(fare.getCurrentPrice())
                .totalPrice(fare.getTotalPrice())
                .fareLabel(fare.getFareLabel())
                .extraSeatSpace(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getExtraSeatSpace() : false)
                .preferredSeatChoice(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getPreferredSeatChoice() : false)
                .advanceSeatSelection(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getAdvanceSeatSelection() : false)
                .guaranteedSeatTogether(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getGuaranteedSeatTogether() : false)
                .priorityBoarding(fare.getBoardingBenefits() != null ? fare.getBoardingBenefits().getPriorityBoarding() : false)
                .priorityCheckin(fare.getBoardingBenefits() != null ? fare.getBoardingBenefits().getPriorityCheckin() : false)
                .fastTrackSecurity(fare.getBoardingBenefits() != null ? fare.getBoardingBenefits().getFastTrackSecurity() : false)
                .complimentaryMeals(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getComplimentaryMeals() : false)
                .premiumMealChoice(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getPremiumMealChoice() : false)
                .inFlightInternet(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getInFlightInternet() : false)
                .inFlightEntertainment(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getInFlightEntertainment() : false)
                .complimentaryBeverages(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getComplimentaryBeverages() : false)
                .freeDateChange(fare.getFlexibilityBenefits() != null ? fare.getFlexibilityBenefits().getFreeDateChange() : false)
                .partialRefund(fare.getFlexibilityBenefits() != null ? fare.getFlexibilityBenefits().getPartialRefund() : false)
                .fullRefund(fare.getFlexibilityBenefits() != null ? fare.getFlexibilityBenefits().getFullRefund() : false)
                .loungeAccess(fare.getPremiumServiceBenefits() != null ? fare.getPremiumServiceBenefits().getLoungeAccess() : false)
                .airportTransfer(fare.getPremiumServiceBenefits() != null ? fare.getPremiumServiceBenefits().getAirportTransfer() : false)
                // todo watch fare rules
//                .fareRulesId(fare.getFa())
//                .fareRules(fare.getFareRules())
//                .baggagePolicy(fare.getBaggagePolicy())
                .createdAt(fare.getCreatedAt())
                .updatedAt(fare.getUpdatedAt())
                .build();
    }

    public static Fare toFare(FareRequest request) {
        if (request == null) return null;

        Double calculatedPrice = request.getCurrentPrice();
        if(calculatedPrice == null) {
            calculatedPrice = request.getBaseFare() + request.getAirlineFees() + request.getTaxesAndFees();
        }

        final SeatBenefits seatBenefits = SeatBenefits.builder()
                .extraSeatSpace(boolValue(request.getExtraSeatSpace()))
                .preferredSeatChoice(boolValue(request.getPreferredSeatChoice()))
                .advanceSeatSelection(boolValue(request.getAdvanceSeatSelection()))
                .guaranteedSeatTogether(boolValue(request.getGuaranteedSeatTogether()))
                .build();

        final FlexibilityBenefits flexibilityBenefits = FlexibilityBenefits.builder()
                .freeDateChange(boolValue(request.getFreeDateChange()))
                .partialRefund(boolValue(request.getPartialRefund()))
                .fullRefund(boolValue(request.getFullRefund()))
                .build();

        final BoardingBenefits boardingBenefits = BoardingBenefits.builder()
                .priorityBoarding(boolValue(request.getPriorityBoarding()))
                .priorityCheckin(boolValue(request.getPriorityCheckin()))
                .fastTrackSecurity(boolValue(request.getFastTrackSecurity()))
                .build();

        final InFlightBenefits inFlightBenefits = InFlightBenefits.builder()
                .complimentaryMeals(boolValue(request.getComplimentaryMeals()))
                .premiumMealChoice(boolValue(request.getPremiumMealChoice()))
                .inFlightInternet(boolValue(request.getInFlightInternet()))
                .inFlightEntertainment(boolValue(request.getInFlightEntertainment()))
                .complimentaryBeverages(boolValue(request.getComplimentaryBeverages()))
                .build();

        final PremiumServiceBenefits premiumServiceBenefits = PremiumServiceBenefits.builder()
                .loungeAccess(boolValue(request.getLoungeAccess()))
                .airportTransfer(boolValue(request.getAirportTransfer()))
                .build();

        return Fare.builder()
                .name(request.getName())
                .rbdCode(request.getRbdCode())
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .baseFare(request.getBaseFare())
                .taxesAndFees(request.getTaxesAndFees())
                .airlineFees(request.getAirlineFees())
                .currentPrice(calculatedPrice)
                .fareLabel(request.getFareLabel())
                .seatBenefits(seatBenefits)
                .flexibilityBenefits(flexibilityBenefits)
                .boardingBenefits(boardingBenefits)
                .inFlightBenefits(inFlightBenefits)
                .premiumServiceBenefits(premiumServiceBenefits)
                .build();
    }

    public static void toFare(FareRequest request, Fare fare) {
        if (request == null || fare == null) return;
        if(request.getName() != null) fare.setName(request.getName());
        if(request.getRbdCode() != null) fare.setRbdCode(request.getRbdCode());
        if(request.getFlightId() != null) fare.setFlightId(request.getFlightId());
        if(request.getCabinClassId() != null) fare.setCabinClassId(request.getCabinClassId());

        if(request.getBaseFare() != null) fare.setBaseFare(request.getBaseFare());
        if(request.getTaxesAndFees() != null) fare.setTaxesAndFees(request.getTaxesAndFees());
        if(request.getAirlineFees() != null) fare.setAirlineFees(request.getAirlineFees());
        if(request.getCurrentPrice() != null) fare.setCurrentPrice(request.getCurrentPrice());
        if(request.getFareLabel() != null) fare.setFareLabel(request.getFareLabel());

        final SeatBenefits seatBenefits = fare.getSeatBenefits();
        if(request.getExtraSeatSpace() != null) seatBenefits.setExtraSeatSpace(request.getExtraSeatSpace());
        if(request.getPreferredSeatChoice() != null) seatBenefits.setPreferredSeatChoice(request.getPreferredSeatChoice());
        if(request.getAdvanceSeatSelection() != null) seatBenefits.setAdvanceSeatSelection(request.getAdvanceSeatSelection());
        if(request.getGuaranteedSeatTogether() != null) seatBenefits.setGuaranteedSeatTogether(request.getGuaranteedSeatTogether());

        final FlexibilityBenefits flexibilityBenefits = fare.getFlexibilityBenefits();
        if(request.getFreeDateChange() != null) flexibilityBenefits.setFreeDateChange(request.getFreeDateChange());
        if(request.getPartialRefund() != null) flexibilityBenefits.setPartialRefund(request.getPartialRefund());
        if(request.getFullRefund() != null) flexibilityBenefits.setFullRefund(request.getFullRefund());

        final BoardingBenefits boardingBenefits = fare.getBoardingBenefits();
        if(request.getPriorityBoarding() != null) boardingBenefits.setPriorityBoarding(request.getPriorityBoarding());
        if(request.getPriorityCheckin() != null) boardingBenefits.setPriorityCheckin(request.getPriorityCheckin());
        if(request.getFastTrackSecurity() != null) boardingBenefits.setFastTrackSecurity(request.getFastTrackSecurity());

        final InFlightBenefits inFlightBenefits = fare.getInFlightBenefits();
        if (request.getComplimentaryMeals() != null) inFlightBenefits.setComplimentaryMeals(request.getComplimentaryMeals());
        if (request.getPremiumMealChoice() != null) inFlightBenefits.setPremiumMealChoice(request.getPremiumMealChoice());
        if (request.getInFlightInternet() != null) inFlightBenefits.setInFlightInternet(request.getInFlightInternet());
        if (request.getInFlightEntertainment() != null) inFlightBenefits.setInFlightEntertainment(request.getInFlightEntertainment());
        if (request.getComplimentaryBeverages() != null) inFlightBenefits.setComplimentaryBeverages(request.getComplimentaryBeverages());

        final PremiumServiceBenefits premiumServiceBenefits = fare.getPremiumServiceBenefits();
        if (request.getLoungeAccess() != null) premiumServiceBenefits.setLoungeAccess(request.getLoungeAccess());
        if (request.getAirportTransfer() != null) premiumServiceBenefits.setAirportTransfer(request.getAirportTransfer());


    }

    private static Boolean boolValue(Boolean value) {
        return value != null ? value : false;
    }
}
