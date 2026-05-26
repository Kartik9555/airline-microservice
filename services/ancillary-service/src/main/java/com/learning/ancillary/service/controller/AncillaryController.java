package com.learning.ancillary.service.controller;

import com.learning.ancillary.service.service.AncillaryService;
import com.learning.common.payload.request.AncillaryRequest;
import com.learning.common.payload.response.AncillaryResponse;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/ancillaries")
public class AncillaryController {
    private final AncillaryService ancillaryService;

    @GetMapping("/{id}")
    public ResponseEntity<AncillaryResponse> getAncillaryById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(ancillaryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AncillaryResponse>> getAncillaryByAirlineId(
            @RequestHeader("X-Airline-Id") Long airlineId) throws Exception {
        return ResponseEntity.ok(ancillaryService.getByAirlineId(airlineId));
    }

    @PostMapping
    public ResponseEntity<AncillaryResponse> createAncillary(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Valid @RequestBody AncillaryRequest request) throws Exception {
        return ResponseEntity.status(CREATED)
                .body(ancillaryService.createAncillary(airlineId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AncillaryResponse> updateAncillary(
            @RequestBody AncillaryRequest request, @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(ancillaryService.updateAncillary(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAncillary(@PathVariable Long id) throws Exception {
        ancillaryService.deleteAncillary(id);
        return ResponseEntity.ok(new ApiResponse("Ancillary has been deleted"));
    }
}
