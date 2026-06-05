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
        if(meal.getCode() != null) request.setCode(meal.getCode());
        if(meal.getName() != null) request.setName(meal.getName());
        if(meal.getDescription() != null) request.setDescription(meal.getDescription());
        if(meal.getPrice() != null) request.setPrice(meal.getPrice());
        if(meal.getRequiresAdvanceBooking() != null) request.setRequiresAdvanceBooking(meal.getRequiresAdvanceBooking());
        if(meal.getImageUrl() != null) request.setImageUrl(meal.getImageUrl());
        if(meal.getCurrency() != null) request.setCurrency(meal.getCurrency());
        if(meal.getDisplayOrder() != null) request.setDisplayOrder(meal.getDisplayOrder());
        if(meal.getAdvanceBookingHours() != null) request.setAdvanceBookingHours(meal.getAdvanceBookingHours());
        if(meal.getMealType() != null) request.setMealType(meal.getMealType());
        if(meal.getDietaryRestriction() != null) request.setDietaryRestriction(meal.getDietaryRestriction());
        if(meal.getIngredients() != null) request.setIngredients(meal.getIngredients());
    }
}
