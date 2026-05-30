package com.learning.seat.service.service;

import com.learning.common.payload.request.SeatMapRequest;
import com.learning.common.payload.response.SeatMapResponse;

public interface SeatMapService {
    SeatMapResponse getSeatMapById(Long id) throws Exception;
    SeatMapResponse getSeatMapByCabinClass(Long cabinClassId) throws Exception;
    SeatMapResponse createSeatMap(Long userId, SeatMapRequest request) throws Exception;
    SeatMapResponse updateSeatMap(Long id, SeatMapRequest request) throws Exception;
    void deleteSeatMap(Long id) throws Exception;
}
