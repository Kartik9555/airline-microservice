package com.learning.flight.ops.service.repository;

import com.learning.flight.ops.service.model.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {
    List<FlightSchedule> findByFlightAirlineId(Long airlineId);
}
