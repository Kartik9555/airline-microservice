package com.learning.flight.ops.service.service.outbound;

import com.learning.common.payload.response.AirportResponse;
import com.learning.flight.ops.service.client.LocationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportOutboundService {

    private final LocationServiceClient client;

    public AirportResponse getAirportById(Long id) {
        return client.getAirportById(id);
    }
}
