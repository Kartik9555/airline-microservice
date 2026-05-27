package com.learning.ancillary.service.controller;

import com.learning.ancillary.service.service.InsuranceCoverageService;
import com.learning.common.payload.request.InsuranceCoverageRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.InsuranceCoverageResponse;
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
@RequestMapping("/api/v1/insurance-coverages")
@RequiredArgsConstructor
public class InsuranceCoverageController {

    private final InsuranceCoverageService insuranceCoverageService;

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceCoverageResponse> getInsuranceCoverageById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(insuranceCoverageService.getInsuranceCoverageById(id));
    }

    @GetMapping
    public ResponseEntity<List<InsuranceCoverageResponse>> getAllInsuranceCoverages() throws Exception {
        return ResponseEntity.ok(insuranceCoverageService.getAllCoverages());
    }

    @GetMapping("/ancillary/{ancillaryId}")
    public ResponseEntity<List<InsuranceCoverageResponse>> getInsuranceCoveragesByAncillaryId(@PathVariable Long ancillaryId) throws Exception {
        return ResponseEntity.ok(insuranceCoverageService.getInsuranceCoveragesByAncillaryId(ancillaryId));
    }

    @GetMapping("/ancillary/{ancillaryId}/active")
    public ResponseEntity<List<InsuranceCoverageResponse>> getActiveInsuranceCoveragesByAncillaryId(@PathVariable Long ancillaryId) throws Exception {
        return ResponseEntity.ok(insuranceCoverageService.getActiveInsuranceCoveragesByAncillaryId(ancillaryId));
    }

    @PostMapping
    public ResponseEntity<InsuranceCoverageResponse> createInsuranceCoverage(
            @Valid @RequestBody InsuranceCoverageRequest request
    ) throws Exception {
        return ResponseEntity.status(CREATED)
                .body(insuranceCoverageService.createInsuranceCoverage(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceCoverageResponse> updateInsuranceCoverage(
            @PathVariable Long id, @RequestBody InsuranceCoverageRequest request
    ) throws Exception {
        return ResponseEntity.ok(insuranceCoverageService.updateInsuranceCoverage(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInsuranceCoverage(@PathVariable Long id) throws Exception {
        insuranceCoverageService.deleteInsuranceCoverageById(id);
        return ResponseEntity.ok(new ApiResponse("Insurance coverage has been deleted"));
    }
}
