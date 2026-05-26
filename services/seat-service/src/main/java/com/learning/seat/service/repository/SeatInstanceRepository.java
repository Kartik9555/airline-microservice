package com.learning.seat.service.repository;

import com.learning.seat.service.model.SeatInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatInstanceRepository extends JpaRepository<SeatInstance, Long> {
}
