package com.learning.ancillary.service.service.impl;

import com.learning.ancillary.service.mapper.FlightMealMapper;
import com.learning.ancillary.service.model.FlightMeal;
import com.learning.ancillary.service.model.Meal;
import com.learning.ancillary.service.repository.FlightMealRepository;
import com.learning.ancillary.service.repository.MealRepository;
import com.learning.ancillary.service.service.FlightMealService;
import com.learning.common.payload.request.FlightMealRequest;
import com.learning.common.payload.response.FlightMealResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightMealServiceImpl implements FlightMealService {

    private final FlightMealRepository flightMealRepository;
    private final MealRepository mealRepository;

    @Override
    @Transactional
    public FlightMealResponse createFlightMeal(FlightMealRequest request) throws Exception {
        final Meal meal = mealRepository.findById(request.getMealId())
                .orElseThrow(() -> new Exception("Meal not found"));

        if(flightMealRepository.existsByFlightIdAndMealId(request.getFlightId(), request.getMealId())) {
            throw new Exception("Flight meal already exists");
        }

        final FlightMeal flightMeal = FlightMealMapper.toFlightMeal(request, meal);
        return FlightMealMapper.toFlightMeal(flightMealRepository.save(flightMeal));
    }

    @Override
    @Transactional(readOnly = true)
    public FlightMealResponse getFlightMealById(Long id) throws Exception {
        final FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight meal not found"));
        return FlightMealMapper.toFlightMeal(flightMeal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightMealResponse> getFlightId(Long flightId) throws Exception {
        return flightMealRepository.findByFlightId(flightId)
                .stream()
                .map(FlightMealMapper::toFlightMeal)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightMealResponse> getAllByIds(List<Long> ids) throws Exception {
        return flightMealRepository.findAllById(ids)
                .stream()
                .map(FlightMealMapper::toFlightMeal)
                .toList();
    }

    @Override
    @Transactional
    public FlightMealResponse updateFlightMeal(Long id, FlightMealRequest request) throws Exception {
        final FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight meal not found"));

        Meal meal = null;
        if(request.getMealId() != null) {
            meal = mealRepository.findById(request.getMealId())
                    .orElseThrow(() -> new Exception("Meal not found"));
        }
        FlightMealMapper.toFlightMeal(request, flightMeal, meal);
        return FlightMealMapper.toFlightMeal(flightMealRepository.save(flightMeal));
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteFlightMeal(Long id) throws Exception {
        final FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight meal not found"));
        flightMealRepository.delete(flightMeal);
    }

    @Override
    @Transactional
    public FlightMealResponse updateFlightMealAvailability(Long id, Boolean availability) throws Exception {
        final FlightMeal flightMeal = flightMealRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight meal not found"));
        flightMeal.setAvailable(availability);
        return FlightMealMapper.toFlightMeal(flightMealRepository.save(flightMeal));
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateMealPrice(List<Long> ids) throws Exception {
        List<FlightMeal> meals = flightMealRepository.findAllById(ids);
        Double mealPrice = 0.0;
        for(FlightMeal flightMeal : meals) {
            mealPrice += flightMeal.getPrice();
        }
        return mealPrice;
    }
}
