package com.learning.common.payload.response;

import com.learning.common.embeddable.Support;
import com.learning.common.enums.AirlineStatus;
import com.learning.common.payload.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineResponse {
    private Long id;
    private String iataCode;
    private String icaoCode;
    private String name;
    private String alias;
    private String logoUrl;
    private String website;
    private AirlineStatus status;
    private String alliance;
    private Instant createdAt;
    private Instant updatedAt;
    private Long ownerId;
    private UserDTO owner;
    private Long updatedById;
    private CityResponse headquarterCity;
    private Support support;
}
