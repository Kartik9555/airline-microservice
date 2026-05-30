package com.learning.flight.ops.service.service.outbound;

import com.learning.common.payload.response.AircraftResponse;
import com.learning.flight.ops.service.client.AirlineCoreServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AircraftOutboundService {

    private final AirlineCoreServiceClient client;

    public AircraftResponse getAircraftById(Long aircraftId) {
        return client.getAircraftById(aircraftId);
    }
}
