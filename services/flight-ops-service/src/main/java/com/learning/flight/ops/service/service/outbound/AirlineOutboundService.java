package com.learning.flight.ops.service.service.outbound;

import com.learning.common.payload.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AirlineOutboundService {

    private final RestTemplate restTemplate;

    public AirlineResponse getAirlineById(Long airlineId) {
        String url = "http://localhost:5002/api/v1/airline/" + airlineId;
        return restTemplate.getForObject(url, AirlineResponse.class);
    }
}
