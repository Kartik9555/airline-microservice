package com.learning.booking.service.client;

import com.learning.common.payload.response.FlightInstanceResponse;
import com.learning.common.payload.response.FlightResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-ops-service")
public interface FlightOpsServiceClient {

    @GetMapping("/api/v1/flights/{id}")
    FlightResponse getFlightById(@PathVariable Long id);

    @GetMapping("/api/v1/flight-instances/{id}")
    FlightInstanceResponse getFlightInstanceById(@PathVariable Long id);
}
