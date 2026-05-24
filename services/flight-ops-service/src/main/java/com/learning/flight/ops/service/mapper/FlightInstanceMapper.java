package com.learning.flight.ops.service.mapper;

import com.learning.common.payload.request.FlightInstanceRequest;
import com.learning.common.payload.response.AircraftResponse;
import com.learning.common.payload.response.AirlineResponse;
import com.learning.common.payload.response.AirportResponse;
import com.learning.common.payload.response.FlightInstanceResponse;
import com.learning.flight.ops.service.model.Flight;
import com.learning.flight.ops.service.model.FlightInstance;

public class FlightInstanceMapper {

    public static FlightInstance toFlightInstance(FlightInstanceRequest request, Flight flight) {
        if (request == null) return null;
        return FlightInstance.builder()
                .airlineId(flight.getAirlineId())
                .flight(flight)
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())
                .scheduleId(request.getScheduleId())
                .departureDateTime(request.getDepartureDateTime())
                .arrivalDateTime(request.getArrivalDateTime())
                .status(request.getStatus())
                .minAdvanceBookingDays(request.getMinAdvanceBookingDays())
                .maxAdvanceBookingDays(request.getMaxAdvanceBookingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : false)
                .build();
    }

    public static FlightInstanceResponse toFlightInstance(FlightInstance flightInstance,
                                                          AircraftResponse  aircraftResponse,
                                                          AirlineResponse airlineResponse,
                                                          AirportResponse departureAirport,
                                                          AirportResponse arrivalAirport) {
            if (flightInstance == null) return null;
            return FlightInstanceResponse.builder()
                    .id(flightInstance.getId())
                    .flightId(flightInstance.getFlight().getId())
                    .flightNumber(flightInstance.getFlight().getFlightNumber())
                    .aircraftCode(aircraftResponse.getCode())
                    .aircraftModel(aircraftResponse.getModel())
                    .aircraftId(aircraftResponse.getId())
                    .airlineId(flightInstance.getAirlineId())
                    .airlineName(airlineResponse.getName())
                    .airlineLogo(airlineResponse.getLogoUrl())
                    .arrivalAirport(arrivalAirport)
                    .departureAirport(departureAirport)
                    .departureDateTime(flightInstance.getDepartureDateTime())
                    .arrivalDateTime(flightInstance.getArrivalDateTime())
                    .formatedDuration(flightInstance.getFormattedDuration())
                    .totalSeats(flightInstance.getTotalSeats())
                    .availableSeats(flightInstance.getAvailableSeats())
                    .minAdvanceBookingDays(flightInstance.getMinAdvanceBookingDays())
                    .maxAdvanceBookingDays(flightInstance.getMaxAdvanceBookingDays())
                    .isActive(flightInstance.getIsActive())
                    .status(flightInstance.getStatus())
                    .build();
        }

        public static void toFlightInstance(FlightInstanceRequest request, FlightInstance flightInstance) {
            if(request == null || flightInstance == null) return;
            if(request.getDepartureAirportId() != null) flightInstance.setDepartureAirportId(request.getDepartureAirportId());
            if(request.getArrivalAirportId() != null) flightInstance.setArrivalAirportId(request.getArrivalAirportId());
            if(request.getDepartureDateTime() != null) flightInstance.setDepartureDateTime(request.getDepartureDateTime());
            if(request.getArrivalDateTime() != null) flightInstance.setArrivalDateTime(request.getArrivalDateTime());
            if(request.getAvailableSeats() != null) flightInstance.setAvailableSeats(request.getAvailableSeats());
            if(request.getStatus() != null) flightInstance.setStatus(request.getStatus());
            if(request.getMinAdvanceBookingDays() != null) flightInstance.setMinAdvanceBookingDays(request.getMinAdvanceBookingDays());
            if(request.getMaxAdvanceBookingDays() != null) flightInstance.setMaxAdvanceBookingDays(request.getMaxAdvanceBookingDays());
            if(request.getIsActive() != null) flightInstance.setIsActive(request.getIsActive());
        }
}
