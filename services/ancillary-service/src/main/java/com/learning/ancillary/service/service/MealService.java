package com.learning.ancillary.service.service;

import com.learning.common.payload.request.MealRequest;
import com.learning.common.payload.response.MealResponse;

import java.util.List;

public interface MealService {
    MealResponse getMealById(Long id) throws Exception;
    MealResponse createMeal(Long userId, MealRequest request) throws Exception;
    MealResponse updateMeal(Long userId, Long id, MealRequest request) throws Exception;
    List<MealResponse> getMealsByAirlineId(Long userId) throws Exception;
    void deleteMealById(Long id) throws Exception;
    void updateAvailability(Long id, Boolean availability) throws Exception;
}
