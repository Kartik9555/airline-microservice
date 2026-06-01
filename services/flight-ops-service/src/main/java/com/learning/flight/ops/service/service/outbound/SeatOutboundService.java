package com.learning.flight.ops.service.service.outbound;

import com.learning.common.enums.CabinClassType;
import com.learning.common.payload.response.CabinClassResponse;
import com.learning.flight.ops.service.client.SeatServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatOutboundService {

    private final SeatServiceClient seatClient;

    public CabinClassResponse getCabinClassByAircraftIdAndName(Long aircraftId, CabinClassType name){
        return seatClient.getCabinClassByAircraftIdAndName(aircraftId, name);
    }
}
