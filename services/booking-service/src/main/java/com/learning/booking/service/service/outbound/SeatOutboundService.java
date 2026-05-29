package com.learning.booking.service.service.outbound;

import com.learning.booking.service.client.SeatServiceClient;
import com.learning.common.payload.response.SeatInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatOutboundService {

    private final SeatServiceClient seatServiceClient;

    public Double calculatePrice(List<Long> seatInstancesId) {
        return seatServiceClient.calculatePrice(seatInstancesId);
    }

    public List<SeatInstanceResponse> getAllSeatInstancesByIds(List<Long> seatInstanceIds) {
        return seatServiceClient.getAllSeatInstancesByIds(seatInstanceIds);
    }
}
