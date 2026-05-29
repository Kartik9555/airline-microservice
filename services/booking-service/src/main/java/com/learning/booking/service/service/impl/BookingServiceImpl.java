package com.learning.booking.service.service.impl;

import com.learning.booking.service.mapper.BookingMapper;
import com.learning.booking.service.model.Booking;
import com.learning.booking.service.model.Passenger;
import com.learning.booking.service.model.Ticket;
import com.learning.booking.service.repository.BookingRepository;
import com.learning.booking.service.service.BookingService;
import com.learning.booking.service.service.PassengerService;
import com.learning.booking.service.service.TicketService;
import com.learning.common.enums.BookingStatus;
import com.learning.common.payload.dto.PaymentDTO;
import com.learning.common.payload.request.BookingRequest;
import com.learning.common.payload.request.PassengerRequest;
import com.learning.common.payload.response.BookingResponse;
import com.learning.common.payload.response.FareResponse;
import com.learning.common.payload.response.FlightCabinAncillaryResponse;
import com.learning.common.payload.response.FlightInstanceResponse;
import com.learning.common.payload.response.FlightMealResponse;
import com.learning.common.payload.response.FlightResponse;
import com.learning.common.payload.response.SeatInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerService passengerService;
    private final TicketService ticketService;

    @Override
    public BookingResponse getBookingById(Long id) throws Exception {
        return bookingRepository.findById(id)
                .map(this::getBookingResponse)
                .orElseThrow(() -> new Exception("Booking not found with id: " + id));
    }

    @Override
    public List<BookingResponse> getBookingsByAirline(Long airlineId, String searchQuery, BookingStatus status, Long flightInstanceId, String sortDirection) {
        final Sort sort = "desc".equalsIgnoreCase(sortDirection) ?
                Sort.by("bookingDate").descending()
                : Sort.by("bookingDate").ascending();

        return bookingRepository.findByAirlineWithFilter(airlineId, searchQuery, status, flightInstanceId, sort)
                .stream()
                .map(this::getBookingResponse)
                .toList()
        ;
    }

    @Override
    public BookingResponse createBooking(Long userId, BookingRequest request) throws Exception {
        String bookingReference = generateBookingReference();
        Set<Passenger> passengers = new HashSet<>();
        for (PassengerRequest passengerRequest : request.getPassengers()) {
            final Passenger passenger = passengerService.createPassenger(userId, passengerRequest);
            passengers.add(passenger);
        }
        // todo flight exists and get airlineId
        final Booking booking = BookingMapper.toBooking(request, userId, passengers, bookingReference);
        booking.setAirlineId(1L);

        List<Long> seatInstanceIds = request.getPassengers().stream().map(PassengerRequest::getSeatInstanceId).toList();
        booking.setSeatInstanceIds(seatInstanceIds);
        final Booking saved = bookingRepository.save(booking);
        passengers.forEach(p -> p.setBooking(saved));
        List<Ticket> tickets = ticketService.generateTicketsForBooking(saved);
        // todo calculate price
        // todo initiate payment using payment service

        saved.setTickets(new HashSet<>(tickets));
        return getBookingResponse(saved);
    }

    @Override
    public BookingResponse updateBooking(Long id, BookingRequest request) throws Exception {
        return null;
    }

    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) throws Exception {
        return bookingRepository.findAllByUserId(userId)
                .stream()
                .map(this::getBookingResponse)
                .toList();
    }

    @Override
    public BookingResponse cancelBooking(Long id) throws Exception {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new Exception("Booking not found with id: " + id));
        booking.setStatus(BookingStatus.CANCELLED);
        return getBookingResponse(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(Long id) throws Exception {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new Exception("Booking not found with id: " + id));
        bookingRepository.delete(booking);
    }

    private String generateBookingReference() {
        String reference;
        do {
            reference = "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (bookingRepository.existsByBookingReference(reference));
        return reference;
    }

    private BookingResponse getBookingResponse(Booking booking) {
        // todo get flight, fare, payment, ancillary, meal details and map to response
        PaymentDTO paymentDTO = PaymentDTO.builder().build();
        FareResponse fareResponse = FareResponse.builder().build();
        FlightResponse flightResponse = FlightResponse.builder().build();
        FlightInstanceResponse flightInstanceResponse = FlightInstanceResponse.builder().build();
        List<FlightCabinAncillaryResponse> ancillaryResponses = new ArrayList<>();
        List<FlightMealResponse> meals  = new ArrayList<>();
        List<SeatInstanceResponse> seat = new ArrayList<>();
        return BookingMapper.toBooking(booking, paymentDTO, fareResponse, flightResponse, flightInstanceResponse, ancillaryResponses, meals, seat);
    }
}
