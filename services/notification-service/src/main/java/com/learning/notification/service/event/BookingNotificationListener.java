package com.learning.notification.service.event;

import com.learning.common.event.BookingConfirmedEvent;
import com.learning.notification.service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingNotificationListener {

    private final EmailService emailService;

    @KafkaListener(topics = "booking_confirmed", groupId = "notification-service-group")
    @Transactional
    public void handleBookingConfirmedEvent(@Payload BookingConfirmedEvent event) throws Exception {
        emailService.sendBookingConfirmation(event);
    }
}
