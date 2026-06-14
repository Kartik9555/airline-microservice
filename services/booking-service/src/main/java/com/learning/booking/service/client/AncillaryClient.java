package com.learning.booking.service.client;

import com.learning.common.payload.response.FlightCabinAncillaryResponse;
import com.learning.common.payload.response.FlightMealResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ancillary-service")
public interface AncillaryClient {

    @GetMapping("/api/v1/flight-cabin-ancillaries/price/total")
    Double calculateAncillaryPrice(@RequestParam("ids") List<Long> ids);

    @GetMapping("/api/v1/flight-cabin-ancillaries")
    List<FlightCabinAncillaryResponse> getAllFlightCabinAncillaryByIds(@RequestParam("ids") List<Long> ids);

    @GetMapping("/api/v1/flight-meals/price/total")
    Double calculateMealPrice(@RequestParam("ids") List<Long> ids);

    @GetMapping("/api/v1/flight-meals/all")
    List<FlightMealResponse> getAllFlightMealByIds(@RequestParam("ids") List<Long> ids);
}
