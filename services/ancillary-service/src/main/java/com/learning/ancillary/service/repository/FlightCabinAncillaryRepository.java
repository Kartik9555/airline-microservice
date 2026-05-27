package com.learning.ancillary.service.repository;

import com.learning.ancillary.service.model.FlightCabinAncillary;
import com.learning.common.enums.AncillaryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightCabinAncillaryRepository extends JpaRepository<FlightCabinAncillary, Long> {
    List<FlightCabinAncillary> findByFlightIdAndCabinClassId(Long flightId, Long cabinClassId);
    Optional<FlightCabinAncillary> findByFlightIdAndCabinClassIdAndAncillaryType(Long flightId, Long cabinClassId, AncillaryType ancillaryType);
    List<FlightCabinAncillary> findAllByFlightIdAndCabinClassIdAndAncillaryType(Long flightId, Long cabinClassId, AncillaryType ancillaryType);
}
