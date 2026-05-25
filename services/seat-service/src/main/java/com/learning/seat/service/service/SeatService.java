package com.learning.seat.service.service;

import com.learning.common.payload.request.SeatRequest;
import com.learning.common.payload.response.SeatMapResponse;
import com.learning.common.payload.response.SeatResponse;

import java.util.List;

public interface SeatService {
    void generateSeat(Long seatMapId) throws Exception;
    List<SeatResponse> getAll();
    SeatMapResponse updateSeats(Long seatId, SeatRequest request) throws Exception;
}
