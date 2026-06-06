package com.learning.seat.service.client;

import com.learning.common.payload.response.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "booking-service")
public interface BookingServiceClient {

    @GetMapping("/api/v1/bookings/{id}")
    BookingResponse getBookingById(@PathVariable("id") Long id);
}
