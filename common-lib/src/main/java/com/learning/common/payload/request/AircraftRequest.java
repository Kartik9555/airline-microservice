package com.learning.common.payload.request;

import com.learning.common.enums.AircraftStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AircraftRequest {
    @NotBlank(message = "Aircraft code is required")
    private String code;

    @NotBlank(message = "Aircraft model is required")
    private String model;

    @NotBlank(message = "Aircraft manufacturer is required")
    private String manufacturer;

    @NotNull(message = "Seating capacity is required")
    @Positive(message = "Seating capacity must be a positive number")
    private Integer seatingCapacity;

    @Positive(message = "Economy seats count must be a positive number")
    private Integer economySeats;

    @Positive(message = "Premium economy seats count must be a positive number")
    private Integer premiumEconomySeats;

    @Positive(message = "Business seats count must be a positive number")
    private Integer businessSeats;

    @Positive(message = "First class seats count must be a positive number")
    private Integer firstClassSeats;

    @Positive(message = "Range must be a positive number")
    private Integer rangeKm;

    @Positive(message = "Cruising speed must be a positive number")
    private Integer cruisingSpeedKmh;

    @Positive(message = "Maximum altitude must be a positive number")
    private Integer maxAltitudeFt;

    @Positive(message = "Year of manufacture must be a positive number")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    @NotNull(message = "Aircraft status is required")
    private AircraftStatus status;

    @NotNull(message = "Availability status is required")
    private Boolean isAvailable;

    private Long currentAirportId;
}
