package com.learning.booking.service.client;

import com.learning.common.payload.response.AirlineResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "airline-core-service")
public interface AirlineCoreServiceClient {

    @GetMapping("/api/v1/airlines/admin")
    AirlineResponse getAirlineByOwner(@RequestHeader("X-User-Id") Long ownerId);

}
