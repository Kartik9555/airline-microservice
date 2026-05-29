package com.learning.payment.service.repository;

import com.learning.payment.service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByBookingIdIn(List<Long> bookingIds);
    Optional<Payment> findByBookingId(Long bookingId);
}
