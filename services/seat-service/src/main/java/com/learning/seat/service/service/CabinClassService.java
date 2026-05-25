package com.learning.seat.service.service;

import com.learning.common.enums.CabinClassType;
import com.learning.common.payload.request.CabinClassRequest;
import com.learning.common.payload.response.CabinClassResponse;

import java.util.List;

public interface CabinClassService {
    CabinClassResponse getCabinClassById(Long id) throws Exception;
    List<CabinClassResponse> getCabinClassByAircraftId(Long aircraftId) throws Exception;
    CabinClassResponse getByAircraftIdAndCabinClass(Long aircraftId, CabinClassType name) throws Exception;
    CabinClassResponse createCabinClass(CabinClassRequest request) throws Exception;
    CabinClassResponse updateCabinClass(Long id, CabinClassRequest request) throws Exception;
    void deleteCabinClassById(Long id) throws Exception;
}
