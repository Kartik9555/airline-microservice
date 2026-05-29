package com.learning.seat.service.service;

import com.learning.common.payload.response.SeatInstanceResponse;

import java.util.List;

public interface SeatInstanceService {
    Double calculateSeatPrice(List<Long> seatInstanceIds);
    List<SeatInstanceResponse> getAllSeatInstancesByIds(List<Long> seatInstanceIds);
}
