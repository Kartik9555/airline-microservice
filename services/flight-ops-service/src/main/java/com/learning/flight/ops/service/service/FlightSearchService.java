package com.learning.flight.ops.service.service;

import com.learning.common.payload.request.FlightSearchRequest;
import com.learning.common.payload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightSearchService {
    Page<FlightInstanceResponse> searchFlights(FlightSearchRequest request, Pageable pageable);
}
