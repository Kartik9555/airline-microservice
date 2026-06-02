package com.learning.booking.service.event.listener;

import com.learning.booking.service.event.publisher.BookingConfirmationEventProducer;
import com.learning.booking.service.repository.BookingRepository;
import com.learning.booking.service.service.outbound.FlightOutboundService;
import com.learning.booking.service.service.outbound.PriceOutboundService;
import com.learning.booking.service.service.outbound.UserOutboundService;
import com.learning.common.enums.BookingStatus;
import com.learning.common.event.PaymentCompletedEvent;
import com.learning.common.event.PaymentFailedEvent;
import com.learning.common.payload.dto.UserDTO;
import com.learning.common.payload.response.FareResponse;
import com.learning.common.payload.response.FlightInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    private final BookingRepository bookingRepository;
    private final FlightOutboundService flightService;
    private final PriceOutboundService priceService;
    private final UserOutboundService userService;
    private final BookingConfirmationEventProducer producer;

    @Transactional
    @KafkaListener(topics = "payment_completed", groupId = "booking-service-group")
    public void handlePaymentCompletedEvent(PaymentCompletedEvent event) {
        bookingRepository.findById(event.getBookingId())
                .ifPresent(booking -> {
                    booking.setStatus(BookingStatus.CONFIRMED);
                    bookingRepository.save(booking);
                    final FlightInstanceResponse flightInstance = flightService.getFlightInstanceById(booking.getFlightInstanceId());
                    final FareResponse fare = priceService.getFareById(booking.getFareId());
                    final UserDTO user = userService.getUserById(booking.getUserId());
                    // publish event for seat service and notification service
                    producer.sendBookingConfirmedEvent(booking, event, flightInstance, fare, user);
                });
    }

    @Transactional
    @KafkaListener(topics = "payment_failed", groupId = "booking-service-group")
    public void handlePaymentFailedEvent(PaymentFailedEvent event) {
        bookingRepository.findById(event.getBookingId())
                .ifPresent(booking -> {
                    booking.setStatus(BookingStatus.CANCELLED);
                    bookingRepository.save(booking);
                });
    }
}
