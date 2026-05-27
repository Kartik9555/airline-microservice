package com.learning.ancillary.service.repository;

import com.learning.ancillary.service.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findMealByAirlineId(Long airlineId);
    boolean existsByCodeAndAirlineId(String code, Long airlineId);
    boolean existsByAirlineIdAndCodeAndIdNot(Long airlineId, String code, Long id);
}
