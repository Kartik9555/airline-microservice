package com.learning.pricing.service.repository;

import com.learning.pricing.service.model.BaggagePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BaggagePolicyRepository extends JpaRepository<BaggagePolicy, Long> {
    Optional<BaggagePolicy> findByFareId(Long fareId) throws Exception;
    List<BaggagePolicy> findByAirlineId(Long airlineId) throws Exception;
    boolean existsByFareId(Long fareId) throws Exception;
}
