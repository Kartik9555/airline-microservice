package com.learning.airline.core.service.service.impl;

import com.learning.airline.core.service.mapper.AirlineMapper;
import com.learning.airline.core.service.model.Airline;
import com.learning.airline.core.service.repository.AirlineRepository;
import com.learning.airline.core.service.service.AirlineService;
import com.learning.common.enums.AirlineStatus;
import com.learning.common.payload.request.AirlineRequest;
import com.learning.common.payload.response.AirlineDropdownItem;
import com.learning.common.payload.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.learning.common.enums.AirlineStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    @Transactional
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) throws Exception {
        if(airlineRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airline with IATA code " + request.getIataCode() + " already exists");
        }

        if(airlineRepository.findByIcaoCode(request.getIcaoCode()).isPresent()) {
            throw new Exception("Airline with ICAO code " + request.getIcaoCode() + " already exists");
        }

        final Airline airline = AirlineMapper.toAirline(request, ownerId);
        final Airline saved = airlineRepository.save(airline);
        return AirlineMapper.toAirline(saved);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "airlinesByOwner", key = "#ownerId")
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        final Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for owner id: " + ownerId));
        return AirlineMapper.toAirline(airline);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "airlines", key = "#id")
    public AirlineResponse getAirlineById(Long airlineId) throws Exception {
        final Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new Exception("Airline not found for id: " + airlineId));
        return AirlineMapper.toAirline(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        return airlineRepository.findAll(pageable)
                .map(AirlineMapper::toAirline);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "airlinesByOwner", key = "#ownerId"),
            @CacheEvict(cacheNames = "airlines", allEntries = true),
            @CacheEvict(cacheNames = "airlinesByIata", allEntries = true),
            @CacheEvict(cacheNames = "airlinesByAlliance", allEntries = true)
    })
    public AirlineResponse updateAirline(Long airlineId,AirlineRequest request, Long ownerId) throws Exception {
        final Airline airline = airlineRepository.findByIdAndOwnerId(airlineId, ownerId)
                .orElseThrow(() -> new Exception("Airline not found for owner id: " + ownerId));

        if(!airline.getIataCode().equals(request.getIataCode()) && airlineRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airline with IATA code " + request.getIataCode() + " already exists");

        }

        if(!airline.getIcaoCode().equals(request.getIcaoCode()) && airlineRepository.findByIcaoCode(request.getIcaoCode()).isPresent()) {
            throw new Exception("Airline with ICAO code " + request.getIcaoCode() + " already exists");

        }

        AirlineMapper.toAirline(airline, request);
        final Airline saved = airlineRepository.save(airline);
        return AirlineMapper.toAirline(saved);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "airlines", key = "#id"),
            @CacheEvict(cacheNames = "airlinesByOwner", allEntries = true),
            @CacheEvict(cacheNames = "airlinesByIata", allEntries = true),
            @CacheEvict(cacheNames = "airlinesByAlliance", allEntries = true)
    })
    public void deleteAirline(Long airlineId, Long ownerId) throws Exception {
        final Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new Exception("Airline not found for id: " + airlineId));

        if(!airline.getOwnerId().equals(ownerId)) {
            throw new Exception("You are not authorized to delete this airline");
        }

        airlineRepository.delete(airline);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "airlines", key = "#airlineId"),
            @CacheEvict(cacheNames = "airlinesByAlliance", allEntries = true)
    })
    public AirlineResponse changeStatus(Long airlineId, AirlineStatus status) throws Exception {
        final Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new Exception("Airline not found for id: " + airlineId));
        airline.setStatus(status);
        final Airline saved = airlineRepository.save(airline);
        return AirlineMapper.toAirline(saved);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "airlinesDropdown")
    public List<AirlineDropdownItem> getAllAirlineDropdownItems() {
        return airlineRepository.findAllByStatus(ACTIVE)
                .stream()
                .map(airline -> AirlineDropdownItem.builder()
                        .id(airline.getId())
                        .name(airline.getName())
                        .iataCode(airline.getIataCode())
                        .icaoCode(airline.getIcaoCode())
                        .logoUrl(airline.getLogoUrl())
                        .build())
                .toList();
    }
}
