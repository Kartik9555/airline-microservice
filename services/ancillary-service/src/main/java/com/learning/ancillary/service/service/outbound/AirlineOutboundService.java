package com.learning.ancillary.service.service.outbound;

import com.learning.ancillary.service.client.AirlineCoreServiceClient;
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
