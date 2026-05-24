package com.learning.flight.ops.service.mapper;

import com.learning.common.payload.request.FlightRequest;
import com.learning.common.payload.response.AircraftResponse;
import com.learning.common.payload.response.AirlineResponse;
import com.learning.common.payload.response.AirportResponse;
import com.learning.common.payload.response.FlightResponse;
import com.learning.flight.ops.service.model.Flight;

public class FlightMapper {

    public static FlightResponse toFlight(Flight flight,
                                          AircraftResponse aircraftResponse,
                                          AirlineResponse airlineResponse,
                                          AirportResponse departureAirport,
                                          AirportResponse arrivalAirport) {
        if (flight == null) return null;
        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(airlineResponse)
                .aircraft(aircraftResponse)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .status(flight.getStatus())
                .createdAt(flight.getCreatedAt())
                .updatedAt(flight.getUpdatedAt())
                .build();
    }

    public static Flight toFlight(FlightRequest request) {
        if (request == null) return null;
        return Flight.builder()
                .flightNumber(request.getFlightNumber())
                .airlineId(request.getAirlineId())
                .aircraftId(request.getAircraftId())
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())
                .build();
    }

    public static void toFlight(FlightRequest request, Flight flight) {
        if (request == null || flight == null) return;
        if(request.getFlightNumber() != null) flight.setFlightNumber(request.getFlightNumber());
        if(request.getAircraftId() != null) flight.setAircraftId(request.getAircraftId());
        if(request.getDepartureAirportId() != null) flight.setDepartureAirportId(request.getDepartureAirportId());
        if(request.getArrivalAirportId() != null) flight.setArrivalAirportId(request.getArrivalAirportId());
        if(request.getStatus() != null) flight.setStatus(request.getStatus());
    }
}
