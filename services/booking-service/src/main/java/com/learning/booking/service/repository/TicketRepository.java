package com.learning.booking.service.repository;

import com.learning.booking.service.model.Passenger;
import com.learning.booking.service.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
        SELECT t FROM Ticket t
        LEFT JOIN FETCH t.booking b
        LEFT JOIN FETCH t.passenger p
        WHERE t.booking.id = :bookingId
    """)
    List<Ticket> findByBookingIdWithDetails(@Param("bookingId") Long bookingId);

    List<Ticket> findByBookingId(Long bookingId);

    boolean existsByTicketNumber(String ticketNumber);
}
