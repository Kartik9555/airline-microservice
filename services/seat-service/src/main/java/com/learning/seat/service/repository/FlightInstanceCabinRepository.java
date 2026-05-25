package com.learning.seat.service.repository;

import com.learning.seat.service.model.FlightInstanceCabin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightInstanceCabinRepository extends JpaRepository<FlightInstanceCabin, Long> {
    Page<FlightInstanceCabin> findByFlightInstanceId(Long flightInstanceId, Pageable pageable);
    Optional<FlightInstanceCabin> findByFlightInstanceIdAndCabinClassId(Long flightInstanceId, Long cabinClassId);
}
