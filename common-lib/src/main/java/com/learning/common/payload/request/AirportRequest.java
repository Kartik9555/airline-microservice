package com.learning.common.payload.request;

import com.learning.common.embeddable.Address;
import com.learning.common.embeddable.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirportRequest {

    @NotBlank(message = "IATA code is required")
    @Size(max = 3, min = 3, message = "IATA code must be exactly 3 characters")
    private String iataCode;

    @NotBlank(message = "Name is required")
    private String name;

    @Valid
    private Address address;

    @NotNull(message = "City ID is required")
    private Long cityId;

    @Valid
    private GeoCode geoCode;
}
