package com.learning.common.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InFlightBenefits {

    @Builder.Default
    @Column(name = "complimentary_meals", nullable = false)
    private Boolean complimentaryMeals = false;

    @Builder.Default
    @Column(name = "premium_meal_choice", nullable = false)
    private Boolean premiumMealChoice = false;

    @Builder.Default
    @Column(name = "in_flight_internet", nullable = false)
    private Boolean inFlightInternet = false;

    @Builder.Default
    @Column(name = "in_flight_entertainment", nullable = false)
    private Boolean inFlightEntertainment = false;

    @Builder.Default
    @Column(name = "complimentary_beverage", nullable = false)
    private Boolean complimentaryBeverages = false;
}
