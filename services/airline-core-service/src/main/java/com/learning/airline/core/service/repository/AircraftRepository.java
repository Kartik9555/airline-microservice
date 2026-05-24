package com.learning.airline.core.service.repository;

import com.learning.airline.core.service.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    List<Aircraft> findByAirlineId(Long airlineId);
    Optional<Aircraft> findByIdAndAirlineId(Long id, Long airlineId);
    boolean existsByCode(String code);
}
