package com.learning.common.payload.request;

import com.learning.common.enums.FlightStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInstanceRequest {

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    private Long scheduleId;

    @NotNull(message = "Departure date and time is required")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date and time is required")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Total seats is required")
    @Positive(message = "Total seats must be a positive number")
    private Integer totalSeats;

    @PositiveOrZero(message = "Available seats must be zero or a positive number")
    private Integer availableSeats;

    @NotNull(message = "Departure airport ID is required")
    private Long departureAirportId;

    @NotNull(message = "Arrival airport ID is required")
    private Long arrivalAirportId;

    private FlightStatus status;
    private Integer minAdvanceBookingDays;
    private Integer maxAdvanceBookingDays;
    private Boolean isActive;


}
