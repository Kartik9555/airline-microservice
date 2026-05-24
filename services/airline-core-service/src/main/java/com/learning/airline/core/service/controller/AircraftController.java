package com.learning.airline.core.service.controller;

import com.learning.airline.core.service.service.AircraftService;
import com.learning.common.payload.request.AircraftRequest;
import com.learning.common.payload.response.AircraftResponse;
import com.learning.common.payload.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/aircraft")
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        return ResponseEntity.status(CREATED).body(aircraftService.createAircraft(request, ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }

    @GetMapping
    public ResponseEntity<List<AircraftResponse>> getAllAircraft(
            @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        return ResponseEntity.ok(aircraftService.getAllAircraftByOwnerId(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(@PathVariable Long id,
                                                           @Valid @RequestBody AircraftRequest request,
                                                           @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        return ResponseEntity.ok(aircraftService.updateAircraft(id, request, ownerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAircraftById(@PathVariable Long id,
                                                           @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        aircraftService.deleteAircraft(id, ownerId);
        return ResponseEntity.ok(new ApiResponse("Aircraft deleted successfully"));
    }
}
