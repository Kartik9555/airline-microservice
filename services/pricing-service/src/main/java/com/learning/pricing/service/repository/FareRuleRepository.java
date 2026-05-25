package com.learning.pricing.service.repository;

import com.learning.pricing.service.model.FareRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FareRuleRepository extends JpaRepository<FareRule, Long> {
    Optional<FareRule> findByFareId(Long fareId);
    List<FareRule> findByAirlineId(Long airlineId);
    boolean existsByFareId(Long fareId);
}
