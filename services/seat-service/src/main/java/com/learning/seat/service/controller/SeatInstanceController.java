package com.learning.seat.service.controller;

import com.learning.common.payload.response.SeatInstanceResponse;
import com.learning.seat.service.service.SeatInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seat-instances")
@RequiredArgsConstructor
public class SeatInstanceController {

    private final SeatInstanceService seatInstanceService;

    @GetMapping("/price/total")
    public ResponseEntity<Double> calculatePrice(
            @RequestParam List<Long> seatInstancesId
    ) {
        return ResponseEntity.ok(seatInstanceService.calculateSeatPrice(seatInstancesId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SeatInstanceResponse>> getAllSeatInstances(
            @RequestParam List<Long> seatInstancesId
    ) {
        return ResponseEntity.ok(seatInstanceService.getAllSeatInstancesByIds(seatInstancesId));
    }
}
