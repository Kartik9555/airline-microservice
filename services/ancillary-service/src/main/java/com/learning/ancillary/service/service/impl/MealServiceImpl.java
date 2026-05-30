package com.learning.ancillary.service.service.impl;

import com.learning.ancillary.service.model.Meal;
import com.learning.ancillary.service.model.MealMapper;
import com.learning.ancillary.service.repository.MealRepository;
import com.learning.ancillary.service.service.MealService;
import com.learning.ancillary.service.service.outbound.AirlineOutboundService;
import com.learning.common.payload.request.MealRequest;
import com.learning.common.payload.response.AirlineResponse;
import com.learning.common.payload.response.MealResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final AirlineOutboundService airlineService;

    @Override
    public MealResponse getMealById(Long id) throws Exception {
        final Meal meal = mealRepository.findById(id).
                orElseThrow(() -> new Exception("Cannot find meal with id " + id));
        return MealMapper.toMeal(meal);
    }

    @Override
    public MealResponse createMeal(Long userId, MealRequest request) throws Exception {
        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        if(mealRepository.existsByCodeAndAirlineId(request.getCode(), airline.getId())) {
            throw new Exception("Meal code already exists for this airline" );
        }
        final Meal meal = MealMapper.toMeal(request, airline.getId());
        return MealMapper.toMeal(mealRepository.save(meal));
    }

    @Override
    public MealResponse updateMeal(Long userId, Long id, MealRequest request) throws Exception {
        final Meal meal = mealRepository.findById(id).
                orElseThrow(() -> new Exception("Cannot find meal with id " + id));

        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        if(mealRepository.existsByAirlineIdAndCodeAndIdNot(airline.getId(), request.getCode(), id)) {
            throw new Exception("Meal code already exists for this airline" );
        }
        MealMapper.toMeal(request, meal);
        return MealMapper.toMeal(mealRepository.save(meal));
    }

    @Override
    public List<MealResponse> getMealsByAirlineId(Long userId) throws Exception {
        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        return mealRepository.findMealByAirlineId(airline.getId()).stream()
                .map(MealMapper::toMeal)
                .toList();
    }

    @Override
    public void deleteMealById(Long id) throws Exception {
        final Meal meal = mealRepository.findById(id).
                orElseThrow(() -> new Exception("Cannot find meal with id " + id));
        mealRepository.delete(meal);
    }

    @Override
    public void updateAvailability(Long id, Boolean availability) throws Exception {
        final Meal meal = mealRepository.findById(id).
                orElseThrow(() -> new Exception("Cannot find meal with id " + id));
        meal.setAvailable(availability);
        mealRepository.save(meal);
    }
}
