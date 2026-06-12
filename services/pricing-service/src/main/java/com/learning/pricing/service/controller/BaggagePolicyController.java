package com.learning.pricing.service.controller;

import com.learning.common.payload.request.BaggagePolicyRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.BaggagePolicyResponse;
import com.learning.pricing.service.service.BaggagePolicyService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/baggage-policies")
@RequiredArgsConstructor
public class BaggagePolicyController {

    private final BaggagePolicyService baggagePolicyService;

    @GetMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyById(id));
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyByFareId(@PathVariable Long fareId) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyByFareId(fareId));
    }

    @GetMapping("/aireline/{airlineId}")
    public ResponseEntity<List<BaggagePolicyResponse>> getBaggagePolicyByAirlineId(@PathVariable Long airlineId) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePoliciesByAirlineId(airlineId));
    }

    @PostMapping
    public ResponseEntity<BaggagePolicyResponse> createBaggagePolicy(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody BaggagePolicyRequest baggagePolicyRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(baggagePolicyService.createBaggagePolicy(userId, baggagePolicyRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> updateBaggagePolicy(@PathVariable Long id, @RequestBody BaggagePolicyRequest request) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.updateBaggagePolicy(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBaggagePolicy(@PathVariable Long id) throws Exception {
        baggagePolicyService.deleteBaggagePolicy(id);
        return ResponseEntity.ok(new ApiResponse("Baggage policy has been deleted"));
    }
}
