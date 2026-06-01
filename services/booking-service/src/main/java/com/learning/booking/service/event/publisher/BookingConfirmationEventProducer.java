package com.learning.booking.service.event.publisher;

import com.learning.booking.service.model.Booking;
import com.learning.booking.service.model.Ticket;
import com.learning.common.embeddable.Baggage;
import com.learning.common.event.BookingConfirmedEvent;
import com.learning.common.event.PassengerNotificationData;
import com.learning.common.event.PaymentCompletedEvent;
import com.learning.common.payload.dto.UserDTO;
import com.learning.common.payload.response.AirportResponse;
import com.learning.common.payload.response.BaggagePolicyResponse;
import com.learning.common.payload.response.CityResponse;
import com.learning.common.payload.response.FareResponse;
import com.learning.common.payload.response.FlightInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingConfirmationEventProducer {

    private final KafkaTemplate<String, BookingConfirmedEvent> kafkaTemplate;

    public void sendBookingConfirmedEvent(Booking booking,
                                          PaymentCompletedEvent payment,
                                          FlightInstanceResponse flight,
                                          FareResponse fare,
                                          UserDTO user) {
        Map<Long, String> ticketByPassenger = booking.getTickets()
                .stream()
                .filter(t -> t.getPassenger()!=null)
                .collect(Collectors.toMap(t -> t.getPassenger().getId(), Ticket::getTicketNumber, (a, b) -> a));

        List<PassengerNotificationData> passengers = booking.getPassengers()
                .stream()
                .map(p -> PassengerNotificationData.builder()
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName())
                        .ticketNumber(ticketByPassenger.getOrDefault(p.getId(), "N/A"))
                        .seatNumber(ticketByPassenger.get(p.getId()))
                        .passportNumber(p.getPassportNumber())
                        .nationality(p.getNationality())
                        .gender(p.getGender() != null ? p.getGender().name() : "")
                        .adult(p.isAdult())
                        .build()
                ).toList();

        String contactEmail = booking.getContactInfo() != null ? booking.getContactInfo().getEmail() : null;
        String contactPhone = booking.getContactInfo() != null ? booking.getContactInfo().getPhone() : null;

        String flightNumber = flight != null ? flight.getFlightNumber() : null;
        String airlineName = flight != null ? flight.getAirlineName() : null;
        String airlineLogo = flight != null ? flight.getAirlineLogo() : null;
        String aircraftModel = flight != null ? flight.getAircraftModel() : null;
        String duration = flight != null ? flight.getFormatedDuration() : null;
        LocalDateTime depTime = flight !=null ? flight.getDepartureDateTime() : null;
        LocalDateTime arrTime = flight !=null ? flight.getArrivalDateTime() : null;

        AirportResponse departureAirport = flight != null ? flight.getDepartureAirport() : null;
        CityResponse departureCity = departureAirport != null ? departureAirport.getCityResponse() : null;
        String depCode = departureAirport != null ? departureAirport.getIataCode() : "N/A";
        String depName = departureAirport != null ? departureAirport.getName() : "N/A";
        String depCityName = departureCity != null ? departureCity.getName() : "N/A";
        String depCountry = departureCity != null ? departureCity.getCountryName() : "N/A";

        AirportResponse arrivalAirport = flight != null ? flight.getArrivalAirport() : null;
        CityResponse arrivalCity = arrivalAirport != null ? arrivalAirport.getCityResponse() : null;
        String arrCode = arrivalAirport != null ? arrivalAirport.getIataCode() : "N/A";
        String arName = arrivalAirport != null ? arrivalAirport.getName() : "N/A";
        String arrCityName = arrivalCity != null ? arrivalCity.getName() : "N/A";
        String arrCountry = arrivalCity != null ? arrivalCity.getCountryName() : "N/A";

        String fareName = fare != null ? fare.getName() : null;
        Double baseFare = fare != null ? fare.getBaseFare() : null;
        Double taxes = fare != null ? fare.getTaxesAndFees() :  null;

        BaggagePolicyResponse baggage = fare != null ? fare.getBaggagePolicy() : null;
        Baggage checkInBaggage = baggage != null ? baggage.getCheckingBaggage() : null;
        Integer ciPieces = checkInBaggage != null ? checkInBaggage.getPieces() : null;
        Double ciWeightPr = checkInBaggage != null ? checkInBaggage.getWeightPerPiece() : null;

        Baggage cabinBaggage = baggage != null ? baggage.getCabinBaggage() : null;
        Integer cbPieces = cabinBaggage != null ? cabinBaggage.getPieces() : null;
        Double cbWeightPr = cabinBaggage != null ? cabinBaggage.getWeightPerPiece() : null;

        final BookingConfirmedEvent event = BookingConfirmedEvent.builder()
                .bookingId(booking.getId())
                .bookingReference(booking.getBookingReference())
                .confirmedAt(Instant.now())
                .bookingDate(booking.getBookingDate())
                .cabinClass(booking.getCabinClassType() != null ? booking.getCabinClassType().name() : "ECONOMY")
                .flexibleTicket(booking.getFlexibleTicket())
                .userId(booking.getUserId())
                .userName(user != null ? user.getFullName() : "Valued Customer")
                .contactEmail(contactEmail)
                .contactPhone(contactPhone)
                .passengers(passengers)
                .flightInstanceId(booking.getFlightInstanceId())
                .flightNumber(flightNumber)
                .airlineName(airlineName)
                .airlineLogo(airlineLogo)
                .aircraftModel(aircraftModel)
                .departureAirportCode(depCode)
                .departureAirportName(depName).departureCity(depCityName)
                .departureCountry(depCountry)
                .departureDateTime(depTime)
                .arrivalAirportCode(arrCode)
                .arrivalAirportName(arName)
                .arrivalCity(arrCityName)
                .arrivalCountry(arrCountry)
                .arrivalDateTime(arrTime)
                .flightDuration(duration)
                .totalAmount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .providerPaymentId(payment.getProviderPaymentId())
                .paymentProvider("RAZORPAY")
                .paidAt(payment.getPaidAt())
                .fareName(fareName)
                .currency("INR")
                .baseFare(baseFare)
                .taxesAndFees(taxes)
                .checkinBaggagePieces(ciPieces)
                .checkinBaggageWeightPerPiece(ciWeightPr)
                .cabinBaggagePieces(cbPieces)
                .cabinBaggageWeightPerPiece(cbWeightPr)
                .freeDateChange(fare != null ? fare.getFreeDateChange() : null)
                .partialRefund(fare != null ? fare.getPartialRefund() : null)
                .fullRefund(fare != null ? fare.getFullRefund() : null)
                .priorityBoarding(fare != null ? fare.getPriorityBoarding() : null)
                .loungeAccess(fare != null ? fare.getLoungeAccess() : null)
                .complimentaryMeals(fare != null ? fare.getComplimentaryMeals() : null)
                .seatInstanceIds(booking.getSeatInstanceIds())
                .build();

        kafkaTemplate.send("booking_confirmed", event);
    }
}
