package com.learning.pricing.service.service.outbound;

import com.learning.common.payload.response.CabinClassResponse;
import com.learning.pricing.service.client.SeatServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatOutboundService {

    private final SeatServiceClient seatClient;

    public CabinClassResponse getCabinClassById(Long id){
        return seatClient.getCabinClassById(id);
    }
}
