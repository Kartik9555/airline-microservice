package com.learning.booking.service.service.outbound;

import com.learning.booking.service.client.AncillaryClient;
import com.learning.common.payload.response.FlightCabinAncillaryResponse;
import com.learning.common.payload.response.FlightMealResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AncillaryOutboundService {

    private final AncillaryClient client;

    public Double calculateAncillaryPrice(List<Long> ancillaryIds) {
        return client.calculateAncillaryPrice(ancillaryIds);
    }

    public Double calculateMealPrice(List<Long> mealIds) {
        return client.calculateMealPrice(mealIds);
    }

    public List<FlightCabinAncillaryResponse> getAllFlightCabinAncillary(List<Long> ids) {
        return client.getAllFlightCabinAncillaryByIds(ids);
    }

    public List<FlightMealResponse> getAllFlightMealByIds(List<Long> ids) {
        return client.getAllFlightMealByIds(ids);
    }
}
