package com.learning.pricing.service.controller;

import com.learning.common.payload.request.FareRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.FareResponse;
import com.learning.pricing.service.service.FareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/fares")
@RequiredArgsConstructor
public class FareController {

    private final FareService fareService;

    @GetMapping("/{id}")
    public ResponseEntity<FareResponse> getFareById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(fareService.getFareById(id));
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}")
    public ResponseEntity<List<FareResponse>> getFareByFlightIdAndCabinClassId(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId) throws Exception {
        return ResponseEntity.ok(fareService.getFareByFlightIdAndCabinClassId(flightId, cabinClassId));
    }

    @GetMapping("/lowest/flight/{flightId}/cabin-class/{cabinClassId}")
    public ResponseEntity<FareResponse> getLowestFareForFlightAndCabinClass(@PathVariable Long flightId, @PathVariable Long cabinClassId) throws Exception {
        return ResponseEntity.ok(fareService.getLowestFareByFlightIdAndCabinClassId(flightId, cabinClassId));
    }

    @PostMapping("/batch-by-ids")
    public ResponseEntity<Map<Long, FareResponse>> getFareByIds(@RequestBody List<Long> fareIds) throws Exception {
        return ResponseEntity.ok(fareService.getFareByIds(fareIds));
    }

    @PostMapping("/search")
    public ResponseEntity<Map<Long, FareResponse>> getLowestFarePerFlight(
            @RequestBody List<Long> flightIds,
            @RequestParam Long cabinClassId) throws Exception {
        return ResponseEntity.ok(fareService.getLowestFarePerFlight(flightIds, cabinClassId));
    }

    @PostMapping
    public ResponseEntity<FareResponse> createFare(@Valid @RequestBody FareRequest fareRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fareService.createFare(fareRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareResponse> updateFare(@PathVariable Long id, @RequestBody FareRequest fareRequest) throws Exception {
        return ResponseEntity.ok(fareService.updateFare(id, fareRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFare(@PathVariable Long id) throws Exception {
        fareService.deleteFareById(id);
        return ResponseEntity.ok(new ApiResponse("Fare deleted successfully"));
    }
}
