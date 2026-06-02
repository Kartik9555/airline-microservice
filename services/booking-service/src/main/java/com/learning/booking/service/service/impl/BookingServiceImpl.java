package com.learning.booking.service.service.impl;

import com.learning.booking.service.mapper.BookingMapper;
import com.learning.booking.service.model.Booking;
import com.learning.booking.service.model.Passenger;
import com.learning.booking.service.model.Ticket;
import com.learning.booking.service.repository.BookingRepository;
import com.learning.booking.service.service.BookingService;
import com.learning.booking.service.service.PassengerService;
import com.learning.booking.service.service.TicketService;
import com.learning.booking.service.service.outbound.AirlineOutboundService;
import com.learning.booking.service.service.outbound.AncillaryOutboundService;
import com.learning.booking.service.service.outbound.FlightOutboundService;
import com.learning.booking.service.service.outbound.PaymentOutboundService;
import com.learning.booking.service.service.outbound.PriceOutboundService;
import com.learning.booking.service.service.outbound.SeatOutboundService;
import com.learning.common.enums.BookingStatus;
import com.learning.common.enums.PaymentProvider;
import com.learning.common.payload.dto.PaymentDTO;
import com.learning.common.payload.request.BookingRequest;
import com.learning.common.payload.request.PassengerRequest;
import com.learning.common.payload.request.PaymentInitiateRequest;
import com.learning.common.payload.response.AirlineResponse;
import com.learning.common.payload.response.BookingResponse;
import com.learning.common.payload.response.FareResponse;
import com.learning.common.payload.response.FlightCabinAncillaryResponse;
import com.learning.common.payload.response.FlightInstanceResponse;
import com.learning.common.payload.response.FlightMealResponse;
import com.learning.common.payload.response.FlightResponse;
import com.learning.common.payload.response.PaymentInitiateResponse;
import com.learning.common.payload.response.SeatInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final FlightOutboundService flightService;
    private final PriceOutboundService priceService;
    private final SeatOutboundService seatService;
    private final AncillaryOutboundService ancillaryService;
    private final PaymentOutboundService paymentService;
    private final AirlineOutboundService airlineService;

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long id) throws Exception {
        return bookingRepository.findById(id)
                .map(this::getBookingResponse)
                .orElseThrow(() -> new Exception("Booking not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByAirline(Long userId, String searchQuery, BookingStatus status, Long flightInstanceId, String sortDirection) {
        final Sort sort = "desc".equalsIgnoreCase(sortDirection) ?
                Sort.by("bookingDate").descending()
                : Sort.by("bookingDate").ascending();
        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        return bookingRepository.findByAirlineWithFilter(airline.getId(), searchQuery, status, flightInstanceId, sort)
                .stream()
                .map(this::getBookingResponse)
                .toList()
        ;
    }

    @Override
    @Transactional
    public PaymentInitiateResponse createBooking(Long userId, BookingRequest request) throws Exception {
        String bookingReference = generateBookingReference();
        Set<Passenger> passengers = new HashSet<>();
        for (PassengerRequest passengerRequest : request.getPassengers()) {
            final Passenger passenger = passengerService.createPassenger(userId, passengerRequest);
            passengers.add(passenger);
        }
        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        final FlightResponse flight = flightService.getFlightById(request.getFlightId());
        final Booking booking = BookingMapper.toBooking(request, userId, passengers, bookingReference);
        booking.setAirlineId(airline.getId());

        List<Long> seatInstanceIds = request.getPassengers().stream().map(PassengerRequest::getSeatInstanceId).toList();
        booking.setSeatInstanceIds(seatInstanceIds);
        final Booking saved = bookingRepository.save(booking);
        passengers.forEach(p -> p.setBooking(saved));
        List<Ticket> tickets = ticketService.generateTicketsForBooking(saved);
        saved.setTickets(new HashSet<>(tickets));

        final Double totalFare = priceService.calculateTotalFare(request.getFareId());
        final Double seatPrice = seatService.calculatePrice(seatInstanceIds);
        final Double ancillaryPrice = ancillaryService.calculateAncillaryPrice(request.getAncillaryIds());
        final Double mealPrice = ancillaryService.calculateMealPrice(request.getMealIds());
        final Double totalPrice = totalFare + seatPrice + ancillaryPrice + mealPrice;

        final PaymentInitiateRequest paymentRequest = PaymentInitiateRequest.builder()
                .userId(userId)
                .bookingId(saved.getId())
                .amount(totalPrice)
                .provider(PaymentProvider.RAZORPAY)
                .description("Booking payment for " + bookingReference)
                .build();
        return paymentService.initiatePayment(paymentRequest);
    }

    @Override
    @Transactional
    public BookingResponse updateBooking(Long id, BookingRequest request) throws Exception {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByUser(Long userId) throws Exception {
        return bookingRepository.findAllByUserId(userId)
                .stream()
                .map(this::getBookingResponse)
                .toList();
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long id) throws Exception {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new Exception("Booking not found with id: " + id));
        booking.setStatus(BookingStatus.CANCELLED);
        return getBookingResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional
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
        PaymentDTO paymentDTO = paymentService.getPaymentById(booking.getPaymentId());
        FareResponse fareResponse = priceService.getFareById(booking.getFareId());
        FlightResponse flightResponse = flightService.getFlightById(booking.getFlightId());
        FlightInstanceResponse flightInstanceResponse = flightService.getFlightInstanceById(booking.getFlightInstanceId());
        List<FlightCabinAncillaryResponse> ancillaryResponses = ancillaryService.getAllFlightCabinAncillary(booking.getAncillaryIds());
        List<FlightMealResponse> meals  = ancillaryService.getAllFlightMealByIds(booking.getMealIds());
        List<SeatInstanceResponse> seat = seatService.getAllSeatInstancesByIds(booking.getSeatInstanceIds());
        return BookingMapper.toBooking(booking, paymentDTO, fareResponse, flightResponse, flightInstanceResponse, ancillaryResponses, meals, seat);
    }
}