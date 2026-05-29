package com.learning.booking.service.service;

import com.learning.common.enums.BookingStatus;
import com.learning.common.payload.request.BookingRequest;
import com.learning.common.payload.response.BookingResponse;
import com.learning.common.payload.response.PaymentInitiateResponse;

import java.util.List;

public interface BookingService {
    BookingResponse getBookingById(Long id) throws Exception;
    List<BookingResponse> getBookingsByAirline(Long airlineId, String searchQuery, BookingStatus status, Long flightInstanceId, String sortDirection) throws Exception;
    PaymentInitiateResponse createBooking(Long userId, BookingRequest request) throws Exception;
    BookingResponse updateBooking(Long id, BookingRequest request) throws Exception;
    List<BookingResponse> getBookingsByUser(Long userId) throws Exception;
    BookingResponse cancelBooking(Long id) throws Exception;
    void deleteBooking(Long id) throws Exception;
}
