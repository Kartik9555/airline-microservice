package com.learning.booking.service.service.outbound;

import com.learning.booking.service.client.FlightOpsServiceClient;
import com.learning.common.payload.response.FlightInstanceResponse;
import com.learning.common.payload.response.FlightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightOutboundService {

    private final FlightOpsServiceClient client;

    public FlightResponse getFlightById(Long id) {
        return client.getFlightById(id);
    }

    public FlightInstanceResponse getFlightInstanceById(Long id) {
        return client.getFlightInstanceById(id);
    }
}
