package com.learning.seat.service.repository;

import com.learning.seat.service.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    boolean existsBySeatMapId(Long seatMapId);
    List<Seat> findBySeatMapId(Long seatMapId);
}
