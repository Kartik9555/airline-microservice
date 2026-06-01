package com.learning.flight.ops.service.service.outbound;

import com.learning.common.payload.response.FareResponse;
import com.learning.flight.ops.service.client.PricingServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceOutboundService {

    private final PricingServiceClient client;

    public FareResponse getLowestFareForFlightAndCabinClass(Long flightId, Long cabinClassId) {
        return client.getLowestFareForFlightAndCabinClass(flightId, cabinClassId);
    }
}
