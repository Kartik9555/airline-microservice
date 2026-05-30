package com.learning.seat.service.service.outbound;

import com.learning.common.payload.response.BookingResponse;
import com.learning.seat.service.client.BookingServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingOutboundService {

    private final BookingServiceClient bookingClient;

    public BookingResponse getBookingById(Long bookingId) {
        return bookingClient.getBookingById(bookingId);
    }
}
