package com.learning.flight.ops.service.service.impl;

import com.learning.common.payload.request.FlightInstanceRequest;
import com.learning.common.payload.response.AircraftResponse;
import com.learning.common.payload.response.AirlineResponse;
import com.learning.common.payload.response.AirportResponse;
import com.learning.common.payload.response.FlightInstanceResponse;
import com.learning.flight.ops.service.mapper.FlightInstanceMapper;
import com.learning.flight.ops.service.model.Flight;
import com.learning.flight.ops.service.model.FlightInstance;
import com.learning.flight.ops.service.repository.FlightInstanceRepository;
import com.learning.flight.ops.service.repository.FlightRepository;
import com.learning.flight.ops.service.service.FlightInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightRepository flightRepository;

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) throws Exception {
        final FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight instance not found with id: " + id));
        return getFlightInstanceResponse(flightInstance);
    }

    @Override
    public Page<FlightInstanceResponse> getByAirlineId(Long airlineId,
                                                       Long departureAirportId,
                                                       Long arrivalAirportId,
                                                       Long flightId,
                                                       LocalDate onDate,
                                                       Pageable pageable) {
        // todo watch airlineId
        final LocalDateTime startDate = onDate != null ? onDate.atStartOfDay() : null;
        final LocalDateTime endDate = onDate != null ? onDate.atTime(23, 59, 59) : null;
        return flightInstanceRepository.findByAirlineId(airlineId, departureAirportId, arrivalAirportId, flightId, startDate, endDate, pageable)
                .map(this::getFlightInstanceResponse);
    }

    @Override
    public FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest request) throws Exception {
        // todo watch airlineId
        final Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new Exception("Flight not found with id: " + request.getFlightId()));

        // todo service to service communication
        final AircraftResponse aircraft = AircraftResponse.builder()
                .id(1L)
                .totalSeats(90)
                .airlineId(airlineId)
                .build();

        final FlightInstance flightInstance = FlightInstanceMapper.toFlightInstance(request, flight);
        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());
        final FlightInstance saved = flightInstanceRepository.save(flightInstance);
        // TODO: create seat instances
        return getFlightInstanceResponse(saved);
    }

    @Override
    public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception {
        final FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight instance not found with id: " + id));

        FlightInstanceMapper.toFlightInstance(request, flightInstance);
        final FlightInstance saved = flightInstanceRepository.save(flightInstance);
        return getFlightInstanceResponse(saved);
    }

    @Override
    public void deleteFlightInstance(Long id) throws Exception {
        final FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight instance not found with id: " + id));
        flightInstanceRepository.delete(flightInstance);
    }

    private FlightInstanceResponse getFlightInstanceResponse(FlightInstance flightInstance) {
        // todo service to service communication
        final AirlineResponse airlineResponse = AirlineResponse.builder().id(flightInstance.getAirlineId()).build();
        final AircraftResponse aircraftResponse = AircraftResponse.builder().airlineId(flightInstance.getAirlineId()).build();
        final AirportResponse departureAirport =  AirportResponse.builder().id(flightInstance.getDepartureAirportId()).build();
        final AirportResponse arrivalAirport =  AirportResponse.builder().id(flightInstance.getArrivalAirportId()).build();
        return FlightInstanceMapper.toFlightInstance(flightInstance, aircraftResponse, airlineResponse, departureAirport, arrivalAirport);
    }
}
