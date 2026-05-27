package com.learning.ancillary.service.mapper;

import com.learning.ancillary.service.model.Ancillary;
import com.learning.ancillary.service.model.FlightCabinAncillary;
import com.learning.ancillary.service.model.InsuranceCoverage;
import com.learning.common.payload.request.FlightCabinAncillaryRequest;
import com.learning.common.payload.response.FlightCabinAncillaryResponse;

import java.util.List;

public class FlightCabinAncillaryMapper {

    public static FlightCabinAncillaryResponse toFlightCabinAncillary(FlightCabinAncillary flightCabinAncillary, List<InsuranceCoverage> coverages) {
        if (flightCabinAncillary == null) return null;
        return FlightCabinAncillaryResponse.builder()
                .id(flightCabinAncillary.getId())
                .flightId(flightCabinAncillary.getFlightId())
                .cabinClassId(flightCabinAncillary.getCabinClassId())
                .ancillary(AncillaryMapper.toAncillary(flightCabinAncillary.getAncillary(), coverages))
                .available(flightCabinAncillary.getAvailable())
                .maxQuantity(flightCabinAncillary.getMaxQuantity())
                .price(flightCabinAncillary.getPrice())
                .includedInFare(flightCabinAncillary.getIncludedInFare())
                .build();
    }

    public static FlightCabinAncillary toFlightCabinAncillary(FlightCabinAncillaryRequest request, Ancillary ancillary) {
        if (request == null) return null;
        return FlightCabinAncillary.builder()
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .ancillary(ancillary)
                .available(request.getAvailable())
                .maxQuantity(request.getMaxQuantity())
                .price(request.getPrice())
                .includedInFare(request.getIncludedInFare())
                .build();
    }

    public static void toFlightCabinAncillary(FlightCabinAncillaryRequest request, FlightCabinAncillary flightCabinAncillary) {
        if (request == null || flightCabinAncillary == null) return;
        flightCabinAncillary.setAvailable(request.getAvailable());
        flightCabinAncillary.setMaxQuantity(request.getMaxQuantity());
        flightCabinAncillary.setPrice(request.getPrice());
        flightCabinAncillary.setIncludedInFare(request.getIncludedInFare());
    }
}
