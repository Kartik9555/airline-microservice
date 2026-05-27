package com.learning.ancillary.service.controller;

import com.learning.ancillary.service.service.FlightCabinAncillaryService;
import com.learning.common.enums.AncillaryType;
import com.learning.common.payload.request.FlightCabinAncillaryRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.FlightCabinAncillaryResponse;
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

@RestController
@RequestMapping("/api/v1/flight-cabin-ancillaries")
@RequiredArgsConstructor
public class FlightCabinAncillaryController {

    private final FlightCabinAncillaryService flightCabinAncillaryService;

    @GetMapping("/{id}")
    public ResponseEntity<FlightCabinAncillaryResponse> getById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightCabinAncillaryService.getFlightCabinAncillaryById(id));
    }

    @GetMapping
    public ResponseEntity<List<FlightCabinAncillaryResponse>> getAllByIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(flightCabinAncillaryService.getAllByIds(ids));
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}")
    public ResponseEntity<List<FlightCabinAncillaryResponse>> getByFlightAndCabinClass(
            @PathVariable Long flightId, @PathVariable Long cabinClassId) {
        return ResponseEntity.ok(flightCabinAncillaryService.getByFlightAndCabinClass(flightId, cabinClassId));
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}/ancillary-type/{ancillaryType}")
    public ResponseEntity<FlightCabinAncillaryResponse> getByFlightAndCabinClassAndAncillaryType(
            @PathVariable Long flightId, @PathVariable Long cabinClassId, @PathVariable AncillaryType ancillaryType) throws Exception {
        return ResponseEntity.ok(flightCabinAncillaryService.getByFlightIdAndCabinClassIdAndAncillaryType(flightId, cabinClassId, ancillaryType));
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}/ancillary-type/{ancillaryType}/all")
    public ResponseEntity<List<FlightCabinAncillaryResponse>> getAllByFlightAndCabinClassAndAncillaryType(
            @PathVariable Long flightId, @PathVariable Long cabinClassId, @PathVariable AncillaryType ancillaryType) {
        return ResponseEntity.ok(flightCabinAncillaryService.getAllByFlightIdAndCabinClassIdAndAncillaryType(flightId, cabinClassId, ancillaryType));
    }

    @PostMapping
    public ResponseEntity<FlightCabinAncillaryResponse> createFlightCabinAncillary(
            @Valid @RequestBody FlightCabinAncillaryRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightCabinAncillaryService.createFlightCabinAncillary(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightCabinAncillaryResponse> updateFlightCabinAncillary(
            @PathVariable Long id, @RequestBody FlightCabinAncillaryRequest request) throws Exception {
        return ResponseEntity.ok(flightCabinAncillaryService.updateFlightCabinAncillary(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightCabinAncillary(@PathVariable Long id) throws Exception {
        flightCabinAncillaryService.deleteFlightCabinAncillary(id);
        return ResponseEntity.ok(new ApiResponse("Flight Cabin Ancillary has been deleted"));
    }

    @GetMapping("/price/total")
    public ResponseEntity<Double> calculateAncillaryPrice(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(flightCabinAncillaryService.calculateAncillaryPrice(ids));
    }

}
