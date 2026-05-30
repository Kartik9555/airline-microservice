package com.learning.seat.service.event;

import com.learning.common.enums.SeatAvailabilityStatus;
import com.learning.common.event.PaymentCompletedEvent;
import com.learning.common.payload.response.BookingResponse;
import com.learning.seat.service.service.SeatInstanceService;
import com.learning.seat.service.service.outbound.BookingOutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    private final BookingOutboundService bookingService;
    private final SeatInstanceService seatInstanceService;

    @KafkaListener(topics = "payment_completed", groupId = "seat-service-group")
    public void handleBookingConfirmed(PaymentCompletedEvent event) {
        final BookingResponse bookingResponse = bookingService.getBookingById(event.getBookingId());
        bookingResponse.getSeatInstances()
                .forEach(seatInstance -> seatInstanceService.updateSeatInstanceStatus(seatInstance.getId(), SeatAvailabilityStatus.BOOKED)
                );
    }
}
