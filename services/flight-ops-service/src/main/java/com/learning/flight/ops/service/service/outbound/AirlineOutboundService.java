package com.learning.flight.ops.service.service.outbound;

import com.learning.common.payload.response.AirlineResponse;
import com.learning.flight.ops.service.client.AircraftCoreServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirlineOutboundService {

    private final AircraftCoreServiceClient client;

    public AirlineResponse getAirlineById(Long airlineId) {
        return client.getAirlineById(airlineId);
    }
}
