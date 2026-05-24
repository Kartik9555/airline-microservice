package com.learning.common.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityRequest {

    @NotBlank(message = "City name is required")
    @Size(max = 100, message = "City name must be at most 100 characters")
    private String name;

    @Size(max = 10, message = "City code must be at most 10 characters")
    @NotBlank(message = "City code is required")
    private String cityCode;

    @Size(max = 10, message = "Country code must be at most 10 characters")
    @NotBlank(message = "Country code is required")
    private String countryCode;

    @Size(max = 100, message = "Country name must be at most 100 characters")
    @NotBlank(message = "Country name is required")
    private String countryName;

    @Size(max = 10, message = "Region code must be at most 10 characters")
    private String regionCode;

    @Size(max = 10, message = "Time Zone Offset must be at most 10 characters")
    private String timeZoneOffset;

}
