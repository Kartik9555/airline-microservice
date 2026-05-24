package com.learning.flight.ops.service.service.impl;

import com.learning.common.enums.FlightStatus;
import com.learning.common.payload.request.FlightRequest;
import com.learning.common.payload.response.AircraftResponse;
import com.learning.common.payload.response.AirlineResponse;
import com.learning.common.payload.response.AirportResponse;
import com.learning.common.payload.response.FlightResponse;
import com.learning.flight.ops.service.mapper.FlightMapper;
import com.learning.flight.ops.service.model.Flight;
import com.learning.flight.ops.service.repository.FlightRepository;
import com.learning.flight.ops.service.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    final FlightRepository flightRepository;

    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest request) throws Exception {
        if(flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new Exception("Flight number already exists");
        }
        final Flight flight = FlightMapper.toFlight(request);
        flight.setAirlineId(airlineId);
        return getFlightResponse(flight);
    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(Long airlineId, Long departureAirportId, Long arrivalAirportId, Pageable pageable) {
        return flightRepository.findByAirlineId(airlineId, departureAirportId, arrivalAirportId, pageable)
                .map(this::getFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) throws Exception {
        final Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight not found with id: " + id));
        return getFlightResponse(flight);
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest request) throws Exception {
        final Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight not found with id: " + id));

        if(flightRepository.existsByFlightNumberAndIdNot(request.getFlightNumber(), id)) {
            throw new Exception("Flight number already exists");
        }
        FlightMapper.toFlight(request, flight);
        return getFlightResponse(flight);
    }

    @Override
    public void deleteFlight(Long airlineId, Long id) throws Exception {
        final Flight flight = flightRepository.findByAirlineIdAndId(airlineId, id)
                .orElseThrow(() -> new Exception("Flight not found with id: " + id));
        flightRepository.delete(flight);
    }

    @Override
    public FlightResponse updateFlightStatus(Long id, FlightStatus status) throws Exception {
        final Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight not found with id: " + id));
        flight.setStatus(status);
        final Flight saved = flightRepository.save(flight);
        return getFlightResponse(saved);
    }

    private FlightResponse getFlightResponse(Flight flight) {
        final Flight saved = flightRepository.save(flight);
        final AircraftResponse aircraftResponse = AircraftResponse.builder().id(saved.getAircraftId()).build();
        final AirlineResponse airlineResponse = AirlineResponse.builder().id(saved.getAirlineId()).build();
        final AirportResponse departureAirport =  AirportResponse.builder().id(saved.getDepartureAirportId()).build();
        final AirportResponse arrivalAirport =  AirportResponse.builder().id(saved.getArrivalAirportId()).build();
        return FlightMapper.toFlight(saved, aircraftResponse, airlineResponse, departureAirport, arrivalAirport);
    }
}
