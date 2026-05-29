package com.learning.booking.service.service.outbound;

import com.learning.booking.service.client.PricingServiceClient;
import com.learning.common.payload.response.FareResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceOutboundService {

    private final PricingServiceClient client;

    public Double calculateTotalFare(Long fareId) {
        final FareResponse fareResponse = getFareById(fareId);
        Double baseFare = fareResponse.getBaseFare();
        Double taxesAndFees = fareResponse.getTaxesAndFees() != null ? fareResponse.getTaxesAndFees() : 0.0;
        Double airlineFees = fareResponse.getAirlineFees()  != null ? fareResponse.getAirlineFees() : 0.0;
        return baseFare + taxesAndFees + airlineFees;
    }

    public FareResponse getFareById(Long fareId) {
        return client.getFareById(fareId);
    }
}
