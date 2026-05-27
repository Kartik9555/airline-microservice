package com.learning.ancillary.service.service;

import com.learning.common.payload.request.FlightMealRequest;
import com.learning.common.payload.response.FlightMealResponse;

import java.util.List;

public interface FlightMealService {
    FlightMealResponse createFlightMeal(FlightMealRequest request) throws Exception;
    FlightMealResponse getFlightMealById(Long id)  throws Exception;
    List<FlightMealResponse> getFlightId(Long flightId) throws Exception;
    List<FlightMealResponse> getAllByIds(List<Long> ids) throws Exception;
    FlightMealResponse updateFlightMeal(Long id, FlightMealRequest request) throws Exception;
    void deleteFlightMeal(Long id) throws Exception;
    FlightMealResponse updateFlightMealAvailability(Long id, Boolean availability) throws Exception;
    Double calculateMealPrice(List<Long> ids) throws Exception;
}
