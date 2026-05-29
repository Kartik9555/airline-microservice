package com.learning.flight.ops.service.client;

import com.learning.common.payload.response.AirportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "location-service")
public interface LocationServiceClient {

    @GetMapping("/api/v1/airports/{id}")
    AirportResponse getAirportById(@PathVariable Long id);
}
