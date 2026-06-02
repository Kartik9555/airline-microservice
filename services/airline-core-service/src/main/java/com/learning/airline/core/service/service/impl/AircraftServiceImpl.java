package com.learning.airline.core.service.service.impl;

import com.learning.airline.core.service.mapper.AircraftMapper;
import com.learning.airline.core.service.model.Aircraft;
import com.learning.airline.core.service.model.Airline;
import com.learning.airline.core.service.repository.AircraftRepository;
import com.learning.airline.core.service.repository.AirlineRepository;
import com.learning.airline.core.service.service.AircraftService;
import com.learning.common.payload.request.AircraftRequest;
import com.learning.common.payload.response.AircraftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "aircrafts", key = "#id")
    public AircraftResponse getAircraftById(Long id) throws Exception {
        return aircraftRepository.findById(id)
                .map(AircraftMapper::toAircraft)
                .orElseThrow(
                    () -> new Exception("Aircraft not found with id: " + id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AircraftResponse> getAllAircraftByOwnerId(Long ownerId) throws Exception {
        final Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () -> new Exception("Airline not found for owner id: " + ownerId)
        );
        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(AircraftMapper::toAircraft)
                .toList();
    }

    @Override
    @Transactional
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
        final Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () -> new Exception("Airline not found for this owner: " + ownerId)
                );

        final Aircraft aircraft = AircraftMapper.toAircraft(request);
        if(aircraftRepository.existsByCode(request.getCode())) {
            throw new Exception("Aircraft with code " + request.getCode() + " already exists.");
        }

        if(request.getSeatingCapacity() < aircraft.getTotalSeats()) {
            throw new Exception("Seating capacity less than requested for aircraft");
        }
        validateAircraftData(aircraft);
        aircraft.setAirline(airline);
        return AircraftMapper.toAircraft(aircraftRepository.save(aircraft));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "aircrafts", key = "#id")
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        final Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () -> new Exception("Airline not found for this owner: " + ownerId)
                );

        Optional<Aircraft> aircraftOpt = aircraftRepository.findByIdAndAirlineId(id, airline.getId());
        if(aircraftOpt.isEmpty()) {
            throw new Exception("Aircraft not found with id: " + id);
        }

        Aircraft aircraft = aircraftOpt.get();
        if(!aircraft.getCode().equals(request.getCode()) && aircraftRepository.existsByCode(request.getCode())) {
            throw new Exception("Aircraft with code " + request.getCode() + " already exists.");
        }
        AircraftMapper.toAircraft(aircraft, request);
        validateAircraftData(aircraft);
        return AircraftMapper.toAircraft(aircraftRepository.save(aircraft));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "aircrafts", key = "#id")
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        final Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () -> new Exception("Airline not found for this owner: " + ownerId)
                );

        final Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId())
                .orElseThrow(
                        () -> new Exception("Aircraft not found with id: " + id)
                );
        aircraftRepository.delete(aircraft);
    }

    private void validateAircraftData(Aircraft aircraft) {
        if (aircraft.getSeatingCapacity() != null && aircraft.getSeatingCapacity() <= 0) {
            throw new IllegalArgumentException("Seating capacity must be positive");
        }

        int totalSpecifiedSeats = (aircraft.getEconomySeats() != null ? aircraft.getEconomySeats() : 0) +
                (aircraft.getPremiumEconomySeats() != null ? aircraft.getPremiumEconomySeats() : 0) +
                (aircraft.getBusinessSeats() != null ? aircraft.getBusinessSeats() : 0) +
                (aircraft.getFirstClassSeats() != null ? aircraft.getFirstClassSeats() : 0);

        if (totalSpecifiedSeats > aircraft.getSeatingCapacity()) {
            throw new IllegalArgumentException("Total specified seats exceed aircraft seating capacity");
        }

        if (aircraft.getYearOfManufacture() != null &&
                (aircraft.getYearOfManufacture() < 1900
                        || aircraft.getYearOfManufacture() > LocalDate.now().getYear())) {
            throw new IllegalArgumentException("Invalid year of manufacture");
        }
    }
}
