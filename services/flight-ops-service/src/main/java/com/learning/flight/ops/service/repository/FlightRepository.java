package com.learning.flight.ops.service.repository;

import com.learning.flight.ops.service.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
    SELECT f FROM Flight f
        WHERE f.airlineId = :airlineId
        AND (:departureAirportId IS NULL OR f.departureAirportId = :departureAirportId)
        AND (:arrivalAirportId IS NULL OR f.arrivalAirportId = :arrivalAirportId)
    """)
    Page<Flight> findByAirlineId(Long airlineId, Long departureAirportId, Long arrivalAirportId, Pageable pageable);
    boolean existsByFlightNumber(String flightNumber);
    boolean existsByFlightNumberAndIdNot(String flightNumber, Long id);
    Optional<Flight> findByAirlineIdAndId(Long airlineId, Long id);
}
