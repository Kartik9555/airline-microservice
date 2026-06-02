package com.learning.location.service.service.impl;

import com.learning.common.payload.request.CityRequest;
import com.learning.common.payload.response.CityResponse;
import com.learning.location.service.mapper.CityMapper;
import com.learning.location.service.model.City;
import com.learning.location.service.repository.CityRepository;
import com.learning.location.service.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest request) throws Exception {
        if(cityRepository.existsByCityCode(request.getCityCode())) {
            throw new Exception("City with code " + request.getCityCode() + " already exists");
        }
        final City city = CityMapper.toCity(request);
        final City saved = cityRepository.save(city);
        return CityMapper.toCity(saved);
    }

    @Override
    public List<CityResponse> createBulkCities(List<CityRequest> requests) throws Exception {
        List<CityResponse> createdCities = new ArrayList<>();
        List<String> skippedCodes = new ArrayList<>();

        for (CityRequest request : requests) {
            try {
                validateCityRequest(request);
            } catch (IllegalArgumentException e) {
                skippedCodes.add(request.getCityCode() + " (invalid: " + e.getMessage() + ")");
                continue;
            }

            if (cityRepository.existsByCityCode(request.getCityCode())) {
                skippedCodes.add(request.getCityCode() + " (already exists)");
                continue;
            }

            City city = CityMapper.toCity(request);
            City savedCity = cityRepository.save(city);
            createdCities.add(CityMapper.toCity(savedCity));
        }

        if (!skippedCodes.isEmpty()) {
            log.info("Bulk city creation - skipped: {}", skippedCodes);
        }
        log.info("Bulk city creation - created {} out of {} cities", createdCities.size(), requests.size());

        return createdCities;
    }

    @Override
    @Cacheable(cacheNames = "cities", key = "#id")
    public CityResponse getCityById(Long id) throws Exception {
        final City city = cityRepository.findById(id).orElseThrow(
                () ->  new Exception("City with id " + id + " not found")
        );
        return CityMapper.toCity(city);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cities", key = "#id"),
            @CacheEvict(cacheNames = "citiesByCode", allEntries = true)
    })
    public CityResponse updateCity(Long id, CityRequest request) throws Exception {
        final City city = cityRepository.findById(id).orElseThrow(
                () ->  new Exception("City with id " + id + " not found")
        );
        validateCityRequest(request, id);
        if(cityRepository.existsByCityCodeAndIdNot(request.getCityCode(), id)) {
            throw new Exception("City with code " + request.getCityCode() + " does not match id " + id);
        }

        CityMapper.toCity(city, request);
        final City saved =cityRepository.save(city);
        return CityMapper.toCity(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cities", key = "#id"),
            @CacheEvict(cacheNames = "citiesByCode", allEntries = true)
    })
    public void deleteCity(Long id) throws Exception {
        final City city = cityRepository.findById(id).orElseThrow(
                () ->  new Exception("City with id " + id + " not found")
        );
        cityRepository.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toCity);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRepository.searchByKeywordIgnoreCase(keyword, pageable).map(CityMapper::toCity);
    }

    @Override
    @Cacheable(cacheNames = "citiesByCode", key = "#countryCode")
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode, pageable).map(CityMapper::toCity);
    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }

    private void validateCityRequest(CityRequest request) {
        validateCityRequest(request, null);
    }

    private void validateCityRequest(CityRequest request, Long excludeId) {
        if (!validateCityCode(request.getCityCode())) {
            throw new IllegalArgumentException("Invalid city code format. Must be 2-10 alphanumeric characters.");
        }

        if (request.getCountryCode() == null || !request.getCountryCode().matches("[A-Z]{2,5}")) {
            throw new IllegalArgumentException("Country code must be 2-5 uppercase letters");
        }

        if (request.getTimeZoneOffset() != null && !request.getTimeZoneOffset().matches("[+-]\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Time zone offset must be in format ±HH:MM");
        }
    }

    public boolean validateCityCode(String cityCode) {
        return cityCode != null && cityCode.length() <= 10 && cityCode.matches("[A-Z0-9]{2,10}");
    }
}
