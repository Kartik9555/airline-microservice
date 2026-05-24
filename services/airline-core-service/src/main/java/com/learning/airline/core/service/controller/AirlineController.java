package com.learning.airline.core.service.controller;

import com.learning.airline.core.service.service.AirlineService;
import com.learning.common.payload.request.AirlineRequest;
import com.learning.common.payload.response.AirlineDropdownItem;
import com.learning.common.payload.response.AirlineResponse;
import com.learning.common.payload.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.learning.common.enums.AirlineStatus.ACTIVE;
import static com.learning.common.enums.AirlineStatus.BANNED;
import static com.learning.common.enums.AirlineStatus.INACTIVE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<AirlineResponse> createAirline(@Valid @RequestBody AirlineRequest request,
                                                         @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        return ResponseEntity.status(CREATED).body(airlineService.createAirline(request, ownerId));
    }

    @GetMapping("/admin")
    public ResponseEntity<AirlineResponse> getAirlineByOwner(@RequestHeader("X-User-Id") Long ownerId) throws Exception {
        return ResponseEntity.ok(airlineService.getAirlineByOwner(ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getAirline(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.getAirlineById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AirlineResponse>> getAllAirline(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws Exception {
        final Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(airlineService.getAllAirlines(pageable));
    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<AirlineDropdownItem>> getAllAirlineDropdownItems() {
        return ResponseEntity.ok(airlineService.getAllAirlineDropdownItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirlineResponse> updateAirline(
            @Valid @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long ownerId,
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.updateAirline(id, request, ownerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirline(@PathVariable Long id, @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        airlineService.deleteAirline(id, ownerId);
        return ResponseEntity.ok(new ApiResponse("Airline deleted successfully"));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<AirlineResponse> approveAirline(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatus(id, ACTIVE));
    }

    @PatchMapping("/{id}/suspend")
    public ResponseEntity<AirlineResponse> suspendAirline(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatus(id, INACTIVE));
    }

    @PatchMapping("/{id}/ban")
    public ResponseEntity<AirlineResponse> banAirline(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatus(id, BANNED));
    }
}
