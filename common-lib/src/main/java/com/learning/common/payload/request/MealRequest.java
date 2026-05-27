package com.learning.common.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealRequest {

    @NotBlank(message = "Meal code is required")
    private String code;

    @NotBlank(message = "Meal name is required")
    private String name;

    private String description;

    @NotBlank(message = "Meal type is required")
    @Size(max = 50, message = "Meal type must be at most 50 characters")
    private String mealType;

    @Size(max = 100, message = "Dietary restriction must be at most 100 characters")
    private String dietaryRestriction;

    @Size(max = 2000, message = "Ingredients must be at most 2000 characters")
    private String ingredients;

    @Size(max = 500, message = "Image URL must be at most 500 characters")
    private String imageUrl;

    private Double price;
    private String currency;
    private Boolean requiresAdvanceBooking;
    private Integer advanceBookingHours;
    private Integer displayOrder;
}
