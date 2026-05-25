package com.learning.seat.service.repository;

import com.learning.seat.service.model.SeatMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatMapRepository extends JpaRepository<SeatMap, Long> {
    Optional<SeatMap> findByCabinClassId(Long cabinClassId);
    boolean existsByAirlineIdAndCabinClassIdAndName(Long airlineId, Long cabinClassId, String cabinClassName);
}
