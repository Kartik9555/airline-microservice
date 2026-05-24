package com.learning.airline.core.service.repository;

import com.learning.airline.core.service.model.Airline;
import com.learning.common.enums.AirlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Optional<Airline> findByIataCode(String iataCode);
    Optional<Airline> findByIcaoCode(String icaoCode);
    Optional<Airline> findByOwnerId(Long ownerId);
    List<Airline> findAllByStatus(AirlineStatus status);

    Optional<Airline> findByIdAndOwnerId(Long id, Long ownerId);
}
