package com.learning.booking.service.mapper;

import com.learning.booking.service.model.Booking;
import com.learning.booking.service.model.Passenger;
import com.learning.common.enums.BookingStatus;
import com.learning.common.payload.dto.PaymentDTO;
import com.learning.common.payload.request.BookingRequest;
import com.learning.common.payload.response.BookingResponse;
import com.learning.common.payload.response.FareResponse;
import com.learning.common.payload.response.FlightCabinAncillaryResponse;
import com.learning.common.payload.response.FlightInstanceResponse;
import com.learning.common.payload.response.FlightMealResponse;
import com.learning.common.payload.response.FlightResponse;
import com.learning.common.payload.response.PassengerResponse;
import com.learning.common.payload.response.SeatInstanceResponse;
import com.learning.common.payload.response.TicketResponse;

import java.util.List;
import java.util.Set;

public class BookingMapper {

    public static BookingResponse toBooking(Booking booking,
                                            PaymentDTO paymentDTO,
                                            FareResponse fareResponse,
                                            FlightResponse flightResponse,
                                            FlightInstanceResponse flightInstanceResponse,
                                            List<FlightCabinAncillaryResponse> ancillaryResponses,
                                            List<FlightMealResponse> meals,
                                            List<SeatInstanceResponse> seats) {
        if (booking == null) return null;

        final List<PassengerResponse> passengers = booking.getPassengers() != null ?
                booking.getPassengers().stream().map(PassengerMapper::toPassenger).toList() : null;

        final List<TicketResponse> tickets = booking.getTickets() != null ?
                booking.getTickets().stream().map(TicketMapper::toTicket).toList() : null;

        return BookingResponse.builder()
                .id(booking.getId())
                .bookingReference(booking.getBookingReference())
                .userId(booking.getUserId())
                .flightId(booking.getFlightId())
                .flightNumber(flightResponse != null ? flightResponse.getFlightNumber() : null)
                .flightName(flightResponse != null && flightResponse.getArrivalAirport() != null && flightResponse.getArrivalAirport().getCityResponse() != null ?
                        flightResponse.getArrivalAirport().getCityResponse().getName() + " -> " + flightResponse.getArrivalAirport().getCityResponse().getId() : null)
                .departureAirport(flightResponse != null ? flightResponse.getDepartureAirport().getName() : null)
                .arrivalAirport(flightResponse != null ? flightResponse.getArrivalAirport().getName() : null)
                .departureTime(flightInstanceResponse != null ? flightInstanceResponse.getDepartureDateTime() : null)
                .arrivalTime(flightInstanceResponse != null ? flightInstanceResponse.getArrivalDateTime() : null)
                .bookingStatus(booking.getStatus())
                .bookingDate(booking.getBookingDate())
                .lastModified(booking.getLastModified())
                .passengers(passengers)
                .totalPassengers(passengers != null ? passengers.size() : 0)
                .seatInstances(seats)
                .ancillaries(ancillaryResponses)
                .meals(meals)
                .tickets(tickets)
                .paymentStatus(paymentDTO != null ? paymentDTO.getPaymentStatus() :  null)
                .fareId(booking.getFareId())
                .fareName(fareResponse != null ? fareResponse.getName() : null)
                .fareBaseFare(fareResponse != null ? fareResponse.getBaseFare() : null)
                .fareTaxesAndFees(fareResponse != null ? fareResponse.getTaxesAndFees() : null)
                .fareAirlineFees(fareResponse != null ? fareResponse.getAirlineFees() : null)
                .totalAmount(fareResponse != null ? fareResponse.getTotalPrice() : null)
                .contactInfo(booking.getContactInfo())
                .build();
    }

    public static Booking toBooking(BookingRequest request, Long userId, Set<Passenger> passengers, String bookingReference) {
        if (request == null) return null;
        return Booking.builder()
                .bookingReference(bookingReference)
                .userId(userId)
                .flightId(request.getFlightId())
                .flightInstanceId(request.getFlightInstanceId())
                .cabinClassType(request.getCabinClass())
                .fareId(request.getFareId())
                .flexibleTicket(request.getIsFlexibleTicket())
                .passengers(passengers)
                .ancillaryIds(request.getAncillaryIds())
                .mealIds(request.getMealIds())
                .status(BookingStatus.PENDING)
                .contactInfo(request.getContactInfo())
                .mealIds(request.getMealIds())
                .build();
    }
}
