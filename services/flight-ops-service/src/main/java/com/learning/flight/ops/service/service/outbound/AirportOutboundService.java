package com.learning.flight.ops.service.service.outbound;

import com.learning.common.payload.response.AirportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AirportOutboundService {

    private final RestTemplate restTemplate;

    public AirportResponse getAirportById(Long id) {
        String url = "http://localhost:5004/api/v1/airports/" + id;
        return restTemplate.getForObject(url, AirportResponse.class);
    }
}
