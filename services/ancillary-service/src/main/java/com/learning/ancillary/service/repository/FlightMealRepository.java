package com.learning.ancillary.service.repository;

import com.learning.ancillary.service.model.FlightMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightMealRepository extends JpaRepository<FlightMeal, Long> {
    List<FlightMeal> findByFlightId(Long flightId);
    boolean existsByFlightIdAndMealId(Long flightId, Long mealId);
}
