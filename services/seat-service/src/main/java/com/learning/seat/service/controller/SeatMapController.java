package com.learning.seat.service.controller;

import com.learning.common.payload.request.SeatMapRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.SeatMapResponse;
import com.learning.seat.service.service.SeatMapService;
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

@RestController
@RequestMapping("/api/v1/seat-maps")
@RequiredArgsConstructor
public class SeatMapController {

    private final SeatMapService seatMapService;

    @GetMapping("/{id}")
    public ResponseEntity<SeatMapResponse> getSeatMapById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(seatMapService.getSeatMapById(id));
    }

    @GetMapping("/cabin-class/{cabinClassId}")
    public ResponseEntity<SeatMapResponse> getSeatMapByCabinClassId(@PathVariable Long cabinClassId) throws Exception{
        return ResponseEntity.ok(seatMapService.getSeatMapByCabinClass(cabinClassId));
    }

    @PostMapping
    public ResponseEntity<SeatMapResponse> createSeatMap(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody SeatMapRequest request) throws Exception{
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seatMapService.createSeatMap(userId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatMapResponse> updateSeatMap(
            @PathVariable Long id, @RequestBody SeatMapRequest request
    ) throws Exception{
        return ResponseEntity.ok(seatMapService.updateSeatMap(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSeatMap(@PathVariable Long id) throws Exception{
        seatMapService.deleteSeatMap(id);
        return ResponseEntity.ok(new ApiResponse("Seat Map has been deleted"));
    }
}
