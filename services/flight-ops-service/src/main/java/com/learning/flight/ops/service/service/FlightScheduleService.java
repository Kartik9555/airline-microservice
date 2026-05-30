package com.learning.flight.ops.service.service;

import com.learning.common.payload.request.FlightScheduleRequest;
import com.learning.common.payload.response.FlightScheduleResponse;

import java.util.List;

public interface FlightScheduleService {
    FlightScheduleResponse getFlightScheduleById(Long id) throws Exception;
    List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId) throws Exception;
    FlightScheduleResponse createFlightSchedule(Long userId, FlightScheduleRequest request) throws Exception;
    FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest request) throws Exception;
    void deleteFlightSchedule(Long id) throws Exception;
}
