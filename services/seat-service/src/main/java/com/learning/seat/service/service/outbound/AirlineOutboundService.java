package com.learning.seat.service.service.outbound;

import com.learning.common.payload.response.AirlineResponse;
import com.learning.seat.service.client.AirlineCoreServiceClient;
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
