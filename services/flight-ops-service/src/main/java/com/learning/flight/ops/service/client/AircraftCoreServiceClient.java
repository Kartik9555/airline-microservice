package com.learning.flight.ops.service.client;

import com.learning.common.payload.response.AircraftResponse;
import com.learning.common.payload.response.AirlineResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "airline-core-service")
public interface AircraftCoreServiceClient {

    @GetMapping("/api/v1/aircrafts/{id}")
    AircraftResponse getAircraftById(@PathVariable Long id);

    @GetMapping("/api/v1/airlines/{id}")
    AirlineResponse getAirlineById(@PathVariable Long id);
}
