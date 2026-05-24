package com.learning.airline.core.service.service;

import com.learning.common.payload.request.AircraftRequest;
import com.learning.common.payload.response.AircraftResponse;

import java.util.List;

public interface AircraftService {
    AircraftResponse getAircraftById(Long id) throws Exception;
    List<AircraftResponse> getAllAircraftByOwnerId(Long ownerId) throws Exception;
    AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception;
    AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception;
    void deleteAircraft(Long id, Long ownerId) throws Exception;

}
