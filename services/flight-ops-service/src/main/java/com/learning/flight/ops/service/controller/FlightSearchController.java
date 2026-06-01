package com.learning.flight.ops.service.controller;

import com.learning.common.enums.CabinClassType;
import com.learning.common.payload.request.FlightSearchRequest;
import com.learning.common.payload.response.FlightInstanceResponse;
import com.learning.flight.ops.service.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1flights")
@RequiredArgsConstructor
public class FlightSearchController {
    private final FlightSearchService flightSearchService;

    @GetMapping("/search")
    public ResponseEntity<Page<FlightInstanceResponse>> searchFlights(
            @RequestParam Long departureAirportId,
            @RequestParam Long arrivalAirportId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate,
            @RequestParam Integer passengers,
            @RequestParam CabinClassType cabinClassType,
            @RequestParam(required = false)List<Long> airlines,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String departureTimeRange,
            @RequestParam(required = false) String arrivalTimeRange,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String alliance,
            Pageable pageable
            ) {
        final FlightSearchRequest request = FlightSearchRequest.builder()
                .departureAirportId(departureAirportId)
                .arrivalAirportId(arrivalAirportId)
                .departureDate(departureDate)
                .passengers(passengers)
                .arrivalDate(arrivalDate)
                .cabinClass(cabinClassType)
                .airlines(airlines)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .departureTimeRange(departureTimeRange)
                .arrivalTimeRange(arrivalTimeRange)
                .maxDuration(maxDuration)
                .sortBy(sortBy)
                .sortOrder(sortOrder)
                .alliance(alliance)
                .build();

        return ResponseEntity.ok(flightSearchService.searchFlights(request, pageable));
    }
}
