package com.learning.location.service.service.impl;

import com.learning.common.payload.request.AirportRequest;
import com.learning.common.payload.response.AirportResponse;
import com.learning.location.service.mapper.AirportMapper;
import com.learning.location.service.model.Airport;
import com.learning.location.service.model.City;
import com.learning.location.service.repository.AirportRepository;
import com.learning.location.service.repository.CityRepository;
import com.learning.location.service.service.AirportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public AirportResponse createAirport(AirportRequest airportRequest) throws Exception {
        if(airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()) {
            throw new Exception("Airport with IATA code " + airportRequest.getIataCode() + " already exists.");
        }
        final City city = cityRepository.findById(airportRequest.getCityId()).orElseThrow(
                () -> new Exception("City not found with id: " + airportRequest.getCityId())
        );
        final Airport airport = AirportMapper.toAirport(airportRequest);
        airport.setCity(city);
        airport.setTimeZoneId(city.getTimeZoneId());
        final Airport saved = airportRepository.save(airport);
        return AirportMapper.toAirport(saved);
    }

    @Override
    @Transactional
    public List<AirportResponse> createBulkAirports(List<AirportRequest> requests)
            throws Exception {
        List<AirportResponse> createdAirports = new ArrayList<>();
        List<String> skippedCodes = new ArrayList<>();

        for (AirportRequest request : requests) {
            if (airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
                skippedCodes.add(request.getIataCode() + " (already exists)");
                continue;
            }

            Optional<City> cityOpt = cityRepository.findById(request.getCityId());
            if (cityOpt.isEmpty()) {
                skippedCodes.add(request.getIataCode() + " (city not found with id: " + request.getCityId() + ")");
                continue;
            }

            Airport airport = AirportMapper.toAirport(request);
            airport.setCity(cityOpt.get());

            Airport savedAirport = airportRepository.save(airport);
            createdAirports.add(AirportMapper.toAirport(savedAirport));
        }

        if (!skippedCodes.isEmpty()) {
            log.info("Bulk airport creation - skipped: {}", skippedCodes);
        }
        log.info("Bulk airport creation - created {} out of {} airports", createdAirports.size(), requests.size());

        return createdAirports;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "airports", key = "#id")
    public AirportResponse getAirportById(Long id) throws Exception {
        final Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport not found with id: " + id));
        return AirportMapper.toAirport(airport);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "allAirports")
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream().map(AirportMapper::toAirport).toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "airportsByCity", key = "#cityId")
    public List<AirportResponse> getAirportsByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId).stream().map(AirportMapper::toAirport).toList();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "airports", key = "#id"),
            @CacheEvict(cacheNames = "allAirports", allEntries = true),
            @CacheEvict(cacheNames = "airportsByIata", allEntries = true),
            @CacheEvict(cacheNames = "airportsByCity", allEntries = true)
    })
    public AirportResponse updateAirport(Long id, AirportRequest airportRequest) throws Exception {
        final Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport not found with id: " + id));

        if(airportRequest.getIataCode() != null &&
                !existingAirport.getIataCode().equals(airportRequest.getIataCode()) &&
        airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()) {
            throw new Exception("Airport with IATA code " + airportRequest.getIataCode() + " already exists.");
        }

        AirportMapper.toAirport(existingAirport, airportRequest);
        final Airport saved = airportRepository.save(existingAirport);
        return AirportMapper.toAirport(saved);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "airports", key = "#id"),
            @CacheEvict(cacheNames = "allAirports", allEntries = true),
            @CacheEvict(cacheNames = "airportsByIata", allEntries = true),
            @CacheEvict(cacheNames = "airportsByCity", allEntries = true)
    })
    public void deleteAirport(Long id) throws Exception {
        airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport not found with id: " + id));
        airportRepository.deleteById(id);
    }
}
