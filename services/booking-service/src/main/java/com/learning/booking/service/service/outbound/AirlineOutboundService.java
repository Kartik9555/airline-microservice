package com.learning.booking.service.service.outbound;

import com.learning.booking.service.client.AirlineCoreServiceClient;
import com.learning.common.payload.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirlineOutboundService {

    private final AirlineCoreServiceClient client;

    public AirlineResponse getAirlineByUserId(Long userId) {
        return client.getAirlineByOwner(userId);
    }
}
