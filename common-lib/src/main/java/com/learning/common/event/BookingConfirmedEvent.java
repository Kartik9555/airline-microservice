package com.learning.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingConfirmedEvent {
    private Long bookingId;
    private String bookingReference;
    private Instant confirmedAt;
    private Instant bookingDate;
    private String cabinClass;
    private boolean flexibleTicket;

    private Long userId;
    private String userName;
    private String contactEmail;
    private String contactPhone;

    private List<PassengerNotificationData> passengers;
    private Long flightInstanceId;
    private String flightNumber;
    private String airlineName;
    private String airlineLogo;
    private String aircraftModel;

    private String departureAirportCode;
    private String departureAirportName;
    private String departureCity;
    private String departureCountry;
    private LocalDateTime departureDateTime;

    private String arrivalAirportCode;
    private String arrivalAirportName;
    private String arrivalCity;
    private String arrivalCountry;
    private LocalDateTime arrivalDateTime;

    private String flightDuration;
    private Double totalAmount;
    private String transactionId;
    private String providerPaymentId;
    private String paymentProvider;
    private Instant paidAt;

    private String fareName;
    private String currency;
    private Double baseFare;
    private Double taxesAndFees;
    private Double seatFees;
    private Double ancillaryFees;
    private Double mealFees;

    private Integer checkinBaggagePieces;
    private Double checkinBaggageWeightPerPiece;
    private Integer cabinBaggagePieces;
    private Double cabinBaggageWeightPerPiece;

    private Boolean freeDateChange;
    private Boolean partialRefund;
    private Boolean fullRefund;
    private Boolean priorityBoarding;
    private Boolean loungeAccess;
    private Boolean complimentaryMeals;

    private List<Long> seatInstanceIds;
}
