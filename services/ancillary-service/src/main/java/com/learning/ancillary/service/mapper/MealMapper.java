package com.learning.ancillary.service.mapper;

import com.learning.ancillary.service.model.Meal;
import com.learning.common.payload.request.MealRequest;
import com.learning.common.payload.response.MealResponse;

public class MealMapper {
    public static MealResponse toMeal(Meal meal) {
        if(meal == null) return null;
        return MealResponse.builder()
                .id(meal.getId())
                .code(meal.getCode())
                .name(meal.getName())
                .description(meal.getDescription())
                .price(meal.getPrice())
                .available(meal.getAvailable())
                .airlineId(meal.getAirlineId())
                .mealType(meal.getMealType())
                .dietaryRestriction(meal.getDietaryRestriction())
                .ingredients(meal.getIngredients())
                .imageUrl(meal.getImageUrl())
                .currency(meal.getCurrency())
                .requiresAdvanceBooking(meal.getRequiresAdvanceBooking())
                .advanceBookingHours(meal.getAdvanceBookingHours())
                .displayOrder(meal.getDisplayOrder())
                .createdAt(meal.getCreatedAt())
                .updatedAt(meal.getUpdatedAt())
                .build();
    }

    public static Meal toMeal(MealRequest request, Long airlineId) {
        if(request == null) return null;
        return Meal.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .mealType(request.getMealType())
                .price(request.getPrice())
                .dietaryRestriction(request.getDietaryRestriction())
                .ingredients(request.getIngredients())
                .imageUrl(request.getImageUrl())
                .currency(request.getCurrency())
                .available(true)
                .requiresAdvanceBooking(request.getRequiresAdvanceBooking() != null ? request.getRequiresAdvanceBooking() : false)
                .advanceBookingHours(request.getAdvanceBookingHours())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .airlineId(airlineId)
                .build();
    }

    public static void toMeal(MealRequest request, Meal meal) {
        if(request == null || meal == null) return;
        if(request.getCode() != null) meal.setCode(request.getCode());
        if(request.getName() != null) meal.setName(request.getName());
        if(request.getDescription() != null) meal.setDescription(request.getDescription());
        if(request.getPrice() != null) meal.setPrice(request.getPrice());
        if(request.getRequiresAdvanceBooking() != null) meal.setRequiresAdvanceBooking(request.getRequiresAdvanceBooking());
        if(request.getImageUrl() != null) meal.setImageUrl(request.getImageUrl());
        if(request.getCurrency() != null) meal.setCurrency(request.getCurrency());
        if(request.getDisplayOrder() != null) meal.setDisplayOrder(request.getDisplayOrder());
        if(request.getAdvanceBookingHours() != null) meal.setAdvanceBookingHours(request.getAdvanceBookingHours());
        if(request.getMealType() != null) meal.setMealType(request.getMealType());
        if(request.getDietaryRestriction() != null) meal.setDietaryRestriction(request.getDietaryRestriction());
        if(request.getIngredients() != null) meal.setIngredients(request.getIngredients());
    }
}
