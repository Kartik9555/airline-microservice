package com.learning.flight.ops.service.mapper;

import com.learning.common.payload.request.FlightScheduleRequest;
import com.learning.common.payload.response.AirportResponse;
import com.learning.common.payload.response.FlightScheduleResponse;
import com.learning.flight.ops.service.model.Flight;
import com.learning.flight.ops.service.model.FlightSchedule;

public class FlightScheduleMapper {

    public static FlightScheduleResponse toFlightSchedule(FlightSchedule flightSchedule, AirportResponse departureAirport, AirportResponse arrivalAirport) {
        if (flightSchedule == null) return null;
        return FlightScheduleResponse.builder()
                .id(flightSchedule.getId())
                .flightId(flightSchedule.getFlight().getId())
                .flightNumber(flightSchedule.getFlight().getFlightNumber())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureTime(flightSchedule.getDepartureTime())
                .arrivalTime(flightSchedule.getArrivalTime())
                .startDate(flightSchedule.getStartDate())
                .endDate(flightSchedule.getEndDate())
                .operatingDays(flightSchedule.getOperatingDays())
                .isActive(flightSchedule.getIsActive())
                .build();
    }

    public static FlightSchedule toFlightSchedule(FlightScheduleRequest request, Flight flight) {
        if (request == null) return null;
        return FlightSchedule.builder()
                .flight(flight)
                .departureAirportId(flight.getDepartureAirportId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .operatingDays(request.getOperatingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public static void toFlightSchedule(FlightScheduleRequest request, FlightSchedule flightSchedule) {
        if (request == null || flightSchedule == null) return;
        if(request.getDepartureTime() != null) flightSchedule.setDepartureTime(request.getDepartureTime());
        if(request.getArrivalTime() != null) flightSchedule.setArrivalTime(request.getArrivalTime());
        if(request.getStartDate() != null) flightSchedule.setStartDate(request.getStartDate());
        if(request.getEndDate() != null) flightSchedule.setEndDate(request.getEndDate());
        if(request.getOperatingDays() != null) flightSchedule.setOperatingDays(request.getOperatingDays());
        if(request.getIsActive() != null) flightSchedule.setIsActive(request.getIsActive());
    }
}
