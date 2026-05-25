package com.learning.seat.service.repository;

import com.learning.common.enums.CabinClassType;
import com.learning.seat.service.model.CabinClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CabinClassRepository extends JpaRepository<CabinClass, Long> {
    List<CabinClass> getByAircraftId(Long aircraftId);
    Optional<CabinClass> findByAircraftIdAndName(Long aircraftId, CabinClassType name);
    boolean existsByAircraftIdAndCode(Long aircraftId, String code);
    boolean existsByAircraftIdAndCodeAndIdNot(Long aircraftId, String code, Long id);
}
