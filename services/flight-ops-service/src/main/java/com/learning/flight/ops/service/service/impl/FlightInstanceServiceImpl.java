package com.learning.flight.ops.service.service.impl;

import com.learning.common.payload.request.FlightInstanceRequest;
import com.learning.common.payload.response.AircraftResponse;
import com.learning.common.payload.response.AirlineResponse;
import com.learning.common.payload.response.AirportResponse;
import com.learning.common.payload.response.FlightInstanceResponse;
import com.learning.flight.ops.service.event.FlightInstanceEventProducer;
import com.learning.flight.ops.service.mapper.FlightInstanceMapper;
import com.learning.flight.ops.service.model.Flight;
import com.learning.flight.ops.service.model.FlightInstance;
import com.learning.flight.ops.service.repository.FlightInstanceRepository;
import com.learning.flight.ops.service.repository.FlightRepository;
import com.learning.flight.ops.service.service.FlightInstanceService;
import com.learning.flight.ops.service.service.outbound.AircraftOutboundService;
import com.learning.flight.ops.service.service.outbound.AirlineOutboundService;
import com.learning.flight.ops.service.service.outbound.AirportOutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightRepository flightRepository;
    private final AirlineOutboundService airlineService;
    private final AircraftOutboundService aircraftService;
    private final AirportOutboundService airportService;
    private final FlightInstanceEventProducer producer;

    @Override
    @Transactional(readOnly = true)
    public FlightInstanceResponse getFlightInstanceById(Long id) throws Exception {
        final FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight instance not found with id: " + id));
        return getFlightInstanceResponse(flightInstance);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FlightInstanceResponse> getByAirlineId(Long userId,
                                                       Long departureAirportId,
                                                       Long arrivalAirportId,
                                                       Long flightId,
                                                       LocalDate onDate,
                                                       Pageable pageable) {
        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        final LocalDateTime startDate = onDate != null ? onDate.atStartOfDay() : null;
        final LocalDateTime endDate = onDate != null ? onDate.atTime(23, 59, 59) : null;
        return flightInstanceRepository.findByAirlineId(airline.getId(), departureAirportId, arrivalAirportId, flightId, startDate, endDate, pageable)
                .map(this::getFlightInstanceResponse);
    }

    @Override
    @Transactional
    public FlightInstanceResponse createFlightInstance(Long userId, FlightInstanceRequest request) throws Exception {
        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        final Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new Exception("Flight not found with id: " + request.getFlightId()));

        final AircraftResponse aircraft = aircraftService.getAircraftById(flight.getAircraftId());
        final FlightInstance flightInstance = FlightInstanceMapper.toFlightInstance(request, flight, airline);
        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());
        final FlightInstance saved = flightInstanceRepository.save(flightInstance);
        // create seat instances, publish kafka event
        producer.sendFlightInstanceCreatedEvent(flightInstance);
        return getFlightInstanceResponse(saved);
    }

    @Override
    @Transactional
    public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception {
        final FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight instance not found with id: " + id));

        FlightInstanceMapper.toFlightInstance(request, flightInstance);
        final FlightInstance saved = flightInstanceRepository.save(flightInstance);
        return getFlightInstanceResponse(saved);
    }

    @Override
    @Transactional
    public void deleteFlightInstance(Long id) throws Exception {
        final FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight instance not found with id: " + id));
        flightInstanceRepository.delete(flightInstance);
    }

    private FlightInstanceResponse getFlightInstanceResponse(FlightInstance flightInstance) {
        final AirlineResponse airlineResponse = airlineService.getAirlineById(flightInstance.getAirlineId());
        final AircraftResponse aircraftResponse = aircraftService.getAircraftById(flightInstance.getFlight().getAircraftId());
        final AirportResponse departureAirport = airportService.getAirportById(flightInstance.getDepartureAirportId());
        final AirportResponse arrivalAirport = airportService.getAirportById(flightInstance.getArrivalAirportId());
        return FlightInstanceMapper.toFlightInstance(flightInstance, aircraftResponse, airlineResponse, departureAirport, arrivalAirport);
    }
}
