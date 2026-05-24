package com.learning.flight.ops.service.service.impl;

import com.learning.common.enums.FlightStatus;
import com.learning.common.payload.request.FlightInstanceRequest;
import com.learning.common.payload.request.FlightScheduleRequest;
import com.learning.common.payload.response.AirportResponse;
import com.learning.common.payload.response.FlightScheduleResponse;
import com.learning.flight.ops.service.mapper.FlightScheduleMapper;
import com.learning.flight.ops.service.model.Flight;
import com.learning.flight.ops.service.model.FlightSchedule;
import com.learning.flight.ops.service.repository.FlightRepository;
import com.learning.flight.ops.service.repository.FlightScheduleRepository;
import com.learning.flight.ops.service.service.FlightInstanceService;
import com.learning.flight.ops.service.service.FlightScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightRepository flightRepository;
    private final FlightInstanceService flightInstanceService;

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) throws Exception {
        final FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight schedule not found with id: " + id));
        return getFlightScheduleResponse(flightSchedule);
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirline(Long airlineId) throws Exception {
        // todo watch airlineId
        return flightScheduleRepository.findByFlightAirlineId(airlineId)
                .stream()
                .map(this::getFlightScheduleResponse)
                .toList();
    }

    @Override
    public FlightScheduleResponse createFlightSchedule(Long airlineId, FlightScheduleRequest request) throws Exception {
        // todo watch airlineId
        final Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new Exception("Flight not found with id: " + request.getFlightId()));

        if(request.getEndDate().isBefore(request.getStartDate())) {
            throw new Exception("End date cannot be before start date");
        }

        final FlightSchedule flightSchedule = FlightScheduleMapper.toFlightSchedule(request, flight);
        final FlightSchedule saved = flightScheduleRepository.save(flightSchedule);

        // create flight instances for the schedule
        final List<DayOfWeek> operatingDays = saved.getOperatingDays();
        final LocalDate startDate = saved.getStartDate();
        final LocalDate endDate = saved.getEndDate();
        final FlightInstanceRequest flightInstanceRequest = FlightInstanceRequest.builder()
                .flightId(flight.getId())
                .scheduleId(saved.getId())
                .departureAirportId(flightSchedule.getDepartureAirportId())
                .arrivalAirportId(flightSchedule.getArrivalAirportId())
                .status(FlightStatus.SCHEDULED)
                .build();

        for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // create flight instance for each operating day
            if(operatingDays.contains(date.getDayOfWeek())) {
                flightInstanceRequest.setDepartureDateTime(LocalDateTime.of(date, saved.getDepartureTime()));
                flightInstanceRequest.setArrivalDateTime(LocalDateTime.of(date, saved.getArrivalTime()));
                flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest);
            }
        }
        return getFlightScheduleResponse(saved);
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest request) throws Exception {
        final FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight schedule not found with id: " + id));
        FlightScheduleMapper.toFlightSchedule(request, flightSchedule);
        final FlightSchedule saved = flightScheduleRepository.save(flightSchedule);
        return getFlightScheduleResponse(saved);
    }

    @Override
    public void deleteFlightSchedule(Long id) throws Exception {
        final FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight schedule not found with id: " + id));
        flightScheduleRepository.delete(flightSchedule);
    }

    private FlightScheduleResponse getFlightScheduleResponse(FlightSchedule flightSchedule) {
        // todo service to service communication
        final AirportResponse departureAirport = AirportResponse.builder()
                .id(flightSchedule.getDepartureAirportId())
                .build();
        final AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flightSchedule.getArrivalAirportId())
                .build();
        return FlightScheduleMapper.toFlightSchedule(flightSchedule, departureAirport, arrivalAirport);
    }
}
