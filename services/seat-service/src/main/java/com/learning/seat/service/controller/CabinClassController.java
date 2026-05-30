package com.learning.seat.service.controller;

import com.learning.common.enums.CabinClassType;
import com.learning.common.payload.request.CabinClassRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.CabinClassResponse;
import com.learning.seat.service.service.CabinClassService;
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
@RequestMapping("/api/v1/cabin-classes")
@RequiredArgsConstructor
public class CabinClassController {

    private final CabinClassService cabinClassService;

    @GetMapping("/{id}")
    public ResponseEntity<CabinClassResponse> getCabinClassById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(cabinClassService.getCabinClassById(id));
    }

    @GetMapping("/aircrat/{aircraftId}")
    public ResponseEntity<List<CabinClassResponse>> getCabinClassByAircraftId(@PathVariable Long aircraftId) throws Exception {
        return ResponseEntity.ok(cabinClassService.getCabinClassByAircraftId(aircraftId));
    }

    @GetMapping("/{name}/aircrat/{aircraftId}")
    public ResponseEntity<CabinClassResponse> getCabinClassByAircraftIdAndName(@PathVariable Long aircraftId, @PathVariable CabinClassType name) throws Exception {
        return ResponseEntity.ok(cabinClassService.getByAircraftIdAndCabinClass(aircraftId, name));
    }

    @PostMapping
    public ResponseEntity<CabinClassResponse> createCabinClass(
            @Valid @RequestBody CabinClassRequest request) throws Exception {
        return ResponseEntity.status(CREATED)
                .body(cabinClassService.createCabinClass(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CabinClassResponse> updateCabinClass(@PathVariable Long id, @RequestBody CabinClassRequest request)  throws Exception {
        return ResponseEntity.ok(cabinClassService.updateCabinClass(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCabinClass(@PathVariable Long id) throws Exception {
        cabinClassService.deleteCabinClassById(id);
        return ResponseEntity.ok(new ApiResponse("Cabin Class has been deleted"));
    }
}
