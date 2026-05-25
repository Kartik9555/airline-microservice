package com.learning.pricing.service.mapper;

import com.learning.common.embeddable.Baggage;
import com.learning.common.payload.request.BaggagePolicyRequest;
import com.learning.common.payload.response.BaggagePolicyResponse;
import com.learning.pricing.service.model.BaggagePolicy;
import com.learning.pricing.service.model.Fare;

public class BaggagePolicyMapper {

    public static BaggagePolicyResponse toBaggagePolicy(BaggagePolicy baggagePolicy) {
        if (baggagePolicy == null) return null;

        final Baggage cabinBaggage = Baggage.builder()
                .maxWeight(baggagePolicy.getCabinBaggageMaxWeight())
                .pieces(baggagePolicy.getCabinBaggagePieces())
                .weightPerPiece(baggagePolicy.getCabinBaggageWeightPerPiece())
                .build();

        final Baggage checkinBaggage = Baggage.builder()
                .maxWeight(baggagePolicy.getCheckinBaggageMaxWeight())
                .pieces(baggagePolicy.getCheckinBaggagePieces())
                .weightPerPiece(baggagePolicy.getCheckinBaggageWeightPerPiece())
                .build();

        return BaggagePolicyResponse.builder()
                .id(baggagePolicy.getId())
                .name(baggagePolicy.getName())
                .description(baggagePolicy.getDescription())
                .cabinBaggage(cabinBaggage)
                .checkingBaggage(checkinBaggage)
                .freeCheckedBagsAllowance(baggagePolicy.getFreeCheckedBagsAllowance())
                .priorityBaggage(baggagePolicy.getPriorityBaggage() != null ? baggagePolicy.getPriorityBaggage() : false)
                .extraBaggageAllowance(baggagePolicy.getExtraBaggageAllowance() != null ? baggagePolicy.getExtraBaggageAllowance() : false)
                .build();
    }

    public static BaggagePolicy toBaggagePolicy(BaggagePolicyRequest request, Fare fare) {
        if (request == null) return null;
        return BaggagePolicy.builder()
                .fare(fare)
                .name(request.getName())
                .description(request.getDescription())
                .cabinBaggageMaxWeight(request.getCabinBaggage().getMaxWeight())
                .cabinBaggagePieces(request.getCabinBaggage().getPieces())
                .cabinBaggageWeightPerPiece(request.getCabinBaggage().getWeightPerPiece())
                .cabinBaggageMaxDimension(request.getCabinBaggage().getMaxDimension())
                .checkinBaggageMaxWeight(request.getCheckinBaggage().getMaxWeight())
                .checkinBaggagePieces(request.getCheckinBaggage().getPieces())
                .checkinBaggageWeightPerPiece(request.getCheckinBaggage().getWeightPerPiece())
                .freeCheckedBagsAllowance(request.getFreeCheckedBagsAllowance())
                .priorityBaggage(request.getPriorityBaggage() != null ? request.getPriorityBaggage() : false)
                .extraBaggageAllowance(request.getExtraBaggageAllowance() != null ? request.getExtraBaggageAllowance() : false)
                .build();
    }

    public static void toBaggagePolicy(BaggagePolicyRequest request, BaggagePolicy baggagePolicy) {
        if (request == null || baggagePolicy == null) return;
        if(request.getName() != null) baggagePolicy.setName(request.getName());
        if(request.getDescription() != null) baggagePolicy.setDescription(request.getDescription());
        if(request.getFreeCheckedBagsAllowance() != null) baggagePolicy.setFreeCheckedBagsAllowance(request.getFreeCheckedBagsAllowance());
        if(request.getPriorityBaggage() != null) baggagePolicy.setPriorityBaggage(request.getPriorityBaggage());
        if(request.getExtraBaggageAllowance() != null) baggagePolicy.setExtraBaggageAllowance(request.getExtraBaggageAllowance());

        if(request.getCabinBaggage() != null) {
            if(request.getCabinBaggage().getMaxWeight() != null) baggagePolicy.setCabinBaggageMaxWeight(request.getCabinBaggage().getMaxWeight());
            if(request.getCabinBaggage().getPieces() != null) baggagePolicy.setCabinBaggagePieces(request.getCabinBaggage().getPieces());
            if(request.getCabinBaggage().getWeightPerPiece() != null) baggagePolicy.setCabinBaggageWeightPerPiece(request.getCabinBaggage().getWeightPerPiece());
            if(request.getCabinBaggage().getMaxDimension() != null) baggagePolicy.setCabinBaggageMaxDimension(request.getCabinBaggage().getMaxDimension());
        }

        if(request.getCheckinBaggage() != null) {
            if(request.getCheckinBaggage().getMaxWeight() != null) baggagePolicy.setCheckinBaggageMaxWeight(request.getCheckinBaggage().getMaxWeight());
            if(request.getCheckinBaggage().getPieces() != null) baggagePolicy.setCheckinBaggagePieces(request.getCheckinBaggage().getPieces());
            if(request.getCheckinBaggage().getWeightPerPiece() != null) baggagePolicy.setCheckinBaggageWeightPerPiece(request.getCheckinBaggage().getWeightPerPiece());
        }
    }
}
