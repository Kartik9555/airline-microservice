package com.learning.location.service.service;

import com.learning.common.payload.request.AirportRequest;
import com.learning.common.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {
    AirportResponse createAirport(AirportRequest airportRequest) throws Exception;
    AirportResponse getAirportById(Long id) throws Exception;
    List<AirportResponse> getAllAirports();
    List<AirportResponse> getAirportsByCityId(Long cityId);
    AirportResponse updateAirport(Long id, AirportRequest airportRequest) throws Exception;
    void deleteAirport(Long id) throws Exception;
}
