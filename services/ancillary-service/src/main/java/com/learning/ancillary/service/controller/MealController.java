package com.learning.ancillary.service.controller;

import com.learning.ancillary.service.service.MealService;
import com.learning.common.payload.request.MealRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.MealResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meals")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @GetMapping("/{id}")
    public ResponseEntity<MealResponse> getMeal(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<List<MealResponse>> getMealByAirline(@RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(mealService.getMealsByAirlineId(userId));
    }

    @PostMapping
    public ResponseEntity<MealResponse> createMeal(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody MealRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                mealService.createMeal(userId, request)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealResponse> updateMeal(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody MealRequest request) throws Exception {
        return ResponseEntity.ok(mealService.updateMeal(userId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMeal(@PathVariable Long id) throws Exception {
        mealService.deleteMealById(id);
        return ResponseEntity.ok(new ApiResponse("Meal has been deleted"));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<ApiResponse> updateAvailability(
            @PathVariable Long id,
            @RequestParam Boolean availability) throws Exception {
        mealService.updateAvailability(id, availability);
        return ResponseEntity.ok(new ApiResponse("Meal availability has been updated"));
    }

}
