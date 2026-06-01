package com.learning.common.payload.request;

import com.learning.common.enums.CabinClassType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchRequest {
    private Long departureAirportId;
    private Long arrivalAirportId;

    @NotNull(message = "Departure Date is required")
    private LocalDate departureDate;

    private LocalDate arrivalDate;

    @Min(value = 1, message = "At least 1 passenger is required")
    private Integer passengers;

    @NotNull(message = "Cabin Class is required")
    private CabinClassType cabinClass;

    private List<Long> airlines;
    private Double minPrice;
    private Double maxPrice;
    private String departureTimeRange;
    private String arrivalTimeRange;
    private Integer maxDuration;
    private String alliance;
    private String sortBy;
    private String sortOrder;
}
