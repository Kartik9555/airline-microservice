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
import com.learning.flight.ops.service.service.outbound.AircraftOutboundService;
import com.learning.flight.ops.service.service.outbound.AirlineOutboundService;
import com.learning.flight.ops.service.service.outbound.AirportOutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirlineOutboundService airlineService;
    private final AircraftOutboundService aircraftService;
    private final AirportOutboundService airportService;

    @Override
    public FlightResponse createFlight(Long userId, FlightRequest request) throws Exception {
        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        if(flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new Exception("Flight number already exists");
        }
        final Flight flight = FlightMapper.toFlight(request);
        flight.setAirlineId(airline.getId());
        final Flight saved = flightRepository.save(flight);
        return getFlightResponse(saved);
    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(Long userId, Long departureAirportId, Long arrivalAirportId, Pageable pageable) {
        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        return flightRepository.findByAirlineId(airline.getId(), departureAirportId, arrivalAirportId, pageable)
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
        final Flight saved = flightRepository.save(flight);
        return getFlightResponse(saved);
    }

    @Override
    public void deleteFlight(Long userId, Long id) throws Exception {
        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        final Flight flight = flightRepository.findByAirlineIdAndId(airline.getId(), id)
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
        final AircraftResponse aircraftResponse = aircraftService.getAircraftById(flight.getAircraftId());
        final AirlineResponse airlineResponse = airlineService.getAirlineById(flight.getAirlineId());
        final AirportResponse departureAirport =  airportService.getAirportById(flight.getDepartureAirportId());
        final AirportResponse arrivalAirport =  airportService.getAirportById(flight.getArrivalAirportId());
        return FlightMapper.toFlight(flight, aircraftResponse, airlineResponse, departureAirport, arrivalAirport);
    }
}
