package com.learning.location.service.controller;

import com.learning.common.payload.request.CityRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.CityResponse;
import com.learning.location.service.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long id) throws Exception {
        final CityResponse cityResponse = cityService.getCityById(id);
        return ResponseEntity.ok(cityResponse);
    }

    @GetMapping
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        final Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        final Pageable pageable = PageRequest.of(page, size, sort);
        final Page<CityResponse> cityResponses = cityService.getAllCities(pageable);
        return ResponseEntity.ok(cityResponses);
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        final Pageable pageable = PageRequest.of(page, size);
        final Page<CityResponse> cityResponses = cityService.getCitiesByCountryCode(countryCode, pageable);
        return ResponseEntity.ok(cityResponses);
    }

    @GetMapping
    public ResponseEntity<Page<CityResponse>> searchCities(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        final Pageable pageable = PageRequest.of(page, size);
        final Page<CityResponse> cityResponses = cityService.searchCities(keyword, pageable);
        return ResponseEntity.ok(cityResponses);
    }

    @GetMapping("/exists/{countryCode}")
    public ResponseEntity<Boolean> existsByCountryCode(@PathVariable String countryCode) {
        final boolean exists = cityService.cityExists(countryCode);
        return ResponseEntity.ok(exists);
    }

    @PostMapping
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest request) throws Exception {
        final CityResponse cityResponse = cityService.createCity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(@Valid @RequestBody CityRequest request, @PathVariable Long id) throws Exception {
        final CityResponse cityResponse = cityService.updateCity(id, request);
        return ResponseEntity.ok(cityResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCity(@PathVariable Long id) throws Exception {
        cityService.deleteCity(id);
        return ResponseEntity.ok(new ApiResponse("City deleted successfully"));
    }
}
