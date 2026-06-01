package com.learning.location.service.service.impl;

import com.learning.common.payload.request.CityRequest;
import com.learning.common.payload.response.CityResponse;
import com.learning.location.service.mapper.CityMapper;
import com.learning.location.service.model.City;
import com.learning.location.service.repository.CityRepository;
import com.learning.location.service.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            @CacheEvict(cacheNames = "citiesByCode", allEntries = true),
    })
    public CityResponse updateCity(Long id, CityRequest request) throws Exception {
        final City city = cityRepository.findById(id).orElseThrow(
                () ->  new Exception("City with id " + id + " not found")
        );

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
            @CacheEvict(cacheNames = "citiesByCode", allEntries = true),
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
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode, pageable).map(CityMapper::toCity);
    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }
}
