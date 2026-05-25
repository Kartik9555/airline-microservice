package com.learning.pricing.service.controller;

import com.learning.common.payload.request.FareRuleRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.FareRuleResponse;
import com.learning.pricing.service.service.FareRuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/fare-rules")
@RequiredArgsConstructor
public class FareRuleController {

    private final FareRuleService fareRuleService;

    @GetMapping("/{id}")
    public ResponseEntity<FareRuleResponse> getFareRuleById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(fareRuleService.getFareRuleById(id));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<FareRuleResponse>> getFareRuleByAirlineId(@PathVariable Long airlineId) throws Exception {
        return ResponseEntity.ok(fareRuleService.getFareRulesByAirlineId(airlineId));
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<FareRuleResponse> getFareRuleByFareId(@PathVariable Long fareId) throws Exception {
        return ResponseEntity.ok(fareRuleService.getFareRuleByFareId(fareId));
    }

    @PostMapping
    public ResponseEntity<FareRuleResponse> createFareRule(
            @Valid @RequestBody FareRuleRequest fareRuleRequest) throws Exception {
        return ResponseEntity.status(CREATED)
                .body(fareRuleService.createFareRule(fareRuleRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareRuleResponse> updateFareRule(@PathVariable Long id, @RequestBody FareRuleRequest request) throws Exception {
        return ResponseEntity.ok(fareRuleService.updateFareRule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFareRule(@PathVariable Long id) throws Exception {
        fareRuleService.deleteFareRule(id);
        return ResponseEntity.ok(new ApiResponse("Successfully deleted fare rule"));
    }
}
