package com.learning.ancillary.service.mapper;

import com.learning.ancillary.service.model.FlightMeal;
import com.learning.ancillary.service.model.Meal;
import com.learning.ancillary.service.model.MealMapper;
import com.learning.common.payload.request.FlightMealRequest;
import com.learning.common.payload.response.FlightMealResponse;

public class FlightMealMapper {

    public static FlightMealResponse toFlightMeal(FlightMeal flightMeal) {
        return FlightMealResponse.builder()
                .id(flightMeal.getId())
                .flightId(flightMeal.getFlightId())
                .available(flightMeal.getAvailable())
                .price(flightMeal.getPrice())
                .displayOrder(flightMeal.getDisplayOrder())
                .meal(MealMapper.toMeal(flightMeal.getMeal()))
                .notes(flightMeal.getNotes())
                .build();
    }

    public static FlightMeal toFlightMeal(FlightMealRequest request, Meal meal) {
        if(request == null) return null;
        return FlightMeal.builder()
                .flightId(request.getFlightId())
                .meal(meal)
                .available(request.getAvailable())
                .price(request.getPrice())
                .displayOrder(request.getDisplayOrder())
                .notes(request.getNotes())
                .build()
        ;
    }

    public static void toFlightMeal(FlightMealRequest request, FlightMeal flightMeal, Meal meal) {
        if(request == null || flightMeal == null) return;
        if (request.getFlightId() != null) flightMeal.setFlightId(request.getFlightId());
        if(meal != null ) flightMeal.setMeal(meal);
        if(request.getAvailable() != null) flightMeal.setAvailable(request.getAvailable());
        if(request.getPrice() != null) flightMeal.setPrice(request.getPrice());
        if(request.getDisplayOrder() != null) flightMeal.setDisplayOrder(request.getDisplayOrder());
        if(request.getNotes() != null) flightMeal.setNotes(request.getNotes());
    }
}
