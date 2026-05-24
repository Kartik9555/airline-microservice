package com.learning.common.payload.request;

import com.learning.common.enums.AirlineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineRequest {

    @NotBlank(message = "IATA Code is required")
    @Size(max = 2, min = 2, message = "IATA Code must be exactly 2 characters")
    private String iataCode;

    @NotBlank(message = "ICAO Code is required")
    @Size(max = 3, min = 3, message = "ICAO Code must be exactly 3 characters")
    private String icaoCode;

    @NotBlank(message = "Airline Name is required")
    private String name;

    private String alias;

    private String logoUrl;
    private String website;
    private AirlineStatus status;
    private String alliance;
    private Long headquarterCityId;
    private String supportEmail;
    private String supportPhone;
    private String supportHours;

}
