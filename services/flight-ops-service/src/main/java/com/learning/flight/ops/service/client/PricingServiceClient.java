package com.learning.flight.ops.service.client;

import com.learning.common.payload.response.FareResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pricing-service")
public interface PricingServiceClient {

    @GetMapping("/api/v1/fares/lowest/flight/{flightId}/cabin-class/{cabinClassId}")
    FareResponse getLowestFareForFlightAndCabinClass(@PathVariable Long flightId, @PathVariable Long cabinClassId);
}
