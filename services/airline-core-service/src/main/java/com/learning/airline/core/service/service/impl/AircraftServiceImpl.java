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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        return aircraftRepository.findById(id)
                .map(AircraftMapper::toAircraft)
                .orElseThrow(
                    () -> new Exception("Aircraft not found with id: " + id)
        );
    }

    @Override
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
        aircraft.setAirline(airline);
        return AircraftMapper.toAircraft(aircraftRepository.save(aircraft));
    }

    @Override
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
        return AircraftMapper.toAircraft(aircraftRepository.save(aircraft));
    }

    @Override
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
}
