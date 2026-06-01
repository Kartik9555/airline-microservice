package com.learning.flight.ops.service.repository;

import com.learning.flight.ops.service.model.FlightInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface FlightInstanceRepository extends JpaRepository<FlightInstance, Long>, JpaSpecificationExecutor<FlightInstance> {

    @Query("""
    SELECT fi FROM FlightInstance fi
        where fi.airlineId = :airlineId
            and (:departureAirportId is null or fi.departureAirportId = :departureAirportId)
            and (:arrivalAirportId is null or fi.arrivalAirportId = :arrivalAirportId)
            and (:flightId is null or fi.flight.id = :flightId)
            and (:dateStart is null or fi.departureDateTime >= :dateStart)
            and (:dateEnd is null or fi.departureDateTime <= :dateEnd)
    """)
    Page<FlightInstance> findByAirlineId(Long airlineId, Long departureAirportId, Long arrivalAirportId, Long flightId, LocalDateTime dateStart, LocalDateTime dateEnd, Pageable pageable);
}
