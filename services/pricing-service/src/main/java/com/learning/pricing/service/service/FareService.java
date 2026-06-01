package com.learning.pricing.service.service;

import com.learning.common.payload.request.FareRequest;
import com.learning.common.payload.response.FareResponse;

import java.util.List;
import java.util.Map;

public interface FareService {
    FareResponse createFare(FareRequest request) throws Exception;
    FareResponse getFareById(Long fareId) throws Exception;
    List<FareResponse> getFareByFlightIdAndCabinClassId(Long flightId, Long cabinClassId) throws Exception;
    FareResponse updateFare(Long id, FareRequest request) throws Exception;
    void deleteFareById(Long fareId) throws Exception;
    List<FareResponse> getFares();
    Map<Long, FareResponse> getLowestFarePerFlight(List<Long> flightIds, Long cabinClassId) throws Exception;
    Map<Long, FareResponse> getFareByIds(List<Long> fareIds) throws Exception;
    FareResponse getLowestFareByFlightIdAndCabinClassId(Long flightId, Long cabinClassId) throws Exception;
}
