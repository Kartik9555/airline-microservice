package com.learning.booking.service.controller;

import com.learning.booking.service.service.BookingService;
import com.learning.common.enums.BookingStatus;
import com.learning.common.payload.request.BookingRequest;
import com.learning.common.payload.response.ApiResponse;
import com.learning.common.payload.response.BookingResponse;
import com.learning.common.payload.response.PaymentInitiateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<List<BookingResponse>> getBookingsByAirline(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) Long flightInstanceId,
            @RequestParam(defaultValue = "DESC") String sortDirection) throws Exception {
        return ResponseEntity.ok(bookingService.getBookingsByAirline(userId, searchQuery, status, flightInstanceId, sortDirection));
    }

    @GetMapping("/user/history")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @PostMapping
    public ResponseEntity<PaymentInitiateResponse> createBooking(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody BookingRequest bookingRequest) throws Exception {
        return ResponseEntity.status(CREATED)
                .body(bookingService.createBooking(userId, bookingRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody BookingRequest bookingRequest) throws Exception {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingRequest));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBooking(@PathVariable Long id) throws Exception {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(new ApiResponse("Booking deleted successfully"));
    }
}
