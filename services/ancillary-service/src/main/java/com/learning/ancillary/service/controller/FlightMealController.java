package com.learning.ancillary.service.controller;

import com.learning.ancillary.service.service.FlightMealService;
import com.learning.common.payload.request.FlightMealRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.FlightMealResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/flight-meals")
@RequiredArgsConstructor
public class FlightMealController {

    private final FlightMealService flightMealService;

    @GetMapping("/{id}")
    public ResponseEntity<FlightMealResponse> getFlightMealById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightMealService.getFlightMealById(id));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<FlightMealResponse>> getFlightMealsByFlightId(@PathVariable Long flightId) throws Exception {
        return ResponseEntity.ok(flightMealService.getFlightId(flightId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlightMealResponse>> getAllByIds(@RequestParam List<Long> ids) throws Exception {
        return ResponseEntity.ok(flightMealService.getAllByIds(ids));
    }

    @PostMapping
    public ResponseEntity<FlightMealResponse> createFlightMeal(
            @Valid @RequestBody FlightMealRequest request) throws Exception {
        return ResponseEntity.status(CREATED).body(flightMealService.createFlightMeal(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightMealResponse> updateFlightMeal(@PathVariable Long id, @RequestBody FlightMealRequest request) throws Exception {
        return ResponseEntity.ok(flightMealService.updateFlightMeal(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightMeal(@PathVariable Long id) throws Exception {
        flightMealService.deleteFlightMeal(id);
        return ResponseEntity.ok(new ApiResponse("Flight meal deleted successfully"));
    }

    @PatchMapping("/{id}/availaility")
    public ResponseEntity<FlightMealResponse> updateFlightMealAvailability(@PathVariable Long id, @RequestParam Boolean available) throws Exception {
        return ResponseEntity.ok(flightMealService.updateFlightMealAvailability(id, available));
    }

    @GetMapping("/price/total")
    public ResponseEntity<Double> calculateMealPrice(@RequestParam List<Long> ids) throws Exception {
        return ResponseEntity.ok(flightMealService.calculateMealPrice(ids));
    }
}
