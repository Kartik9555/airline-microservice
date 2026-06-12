package com.learning.pricing.service.service.outbound;

import com.learning.common.payload.response.AirlineResponse;
import com.learning.pricing.service.client.AirlineCoreServiceClient;
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
