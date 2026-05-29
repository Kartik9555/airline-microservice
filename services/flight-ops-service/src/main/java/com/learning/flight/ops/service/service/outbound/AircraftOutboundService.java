package com.learning.flight.ops.service.service.outbound;

import com.learning.common.payload.response.AircraftResponse;
import com.learning.flight.ops.service.client.AircraftCoreServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AircraftOutboundService {

    private final AircraftCoreServiceClient client;

    public AircraftResponse getAircraftById(Long aircraftId) {
        return client.getAircraftById(aircraftId);
    }
}
