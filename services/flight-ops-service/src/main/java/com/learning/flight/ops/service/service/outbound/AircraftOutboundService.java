package com.learning.flight.ops.service.service.outbound;

import com.learning.common.payload.response.AircraftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AircraftOutboundService {
    private final RestTemplate restTemplate;

    public AircraftResponse getAircraftById(Long aircraftId) {
        String url = "http://localhost:5002/api/v1/aircraft" + aircraftId;
        return restTemplate.getForObject(url, AircraftResponse.class);
    }
}
