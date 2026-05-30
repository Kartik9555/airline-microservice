package com.learning.flight.ops.service.service;

import com.learning.common.enums.FlightStatus;
import com.learning.common.payload.request.FlightRequest;
import com.learning.common.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightService {
    FlightResponse createFlight(Long userId, FlightRequest flightRequest) throws Exception;
    Page<FlightResponse> getFlightsByAirline(Long userId, Long departureAirportId, Long arrivalAirportId, Pageable pageable);
    FlightResponse getFlightById(Long id) throws Exception;
    FlightResponse updateFlight(Long id, FlightRequest flightRequest) throws Exception;
    void deleteFlight(Long userId, Long id) throws Exception;
    FlightResponse updateFlightStatus(Long id, FlightStatus status) throws Exception;
}
