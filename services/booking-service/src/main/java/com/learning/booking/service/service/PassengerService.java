package com.learning.booking.service.service;

import com.learning.booking.service.model.Passenger;
import com.learning.common.payload.request.PassengerRequest;

public interface PassengerService {
    Passenger createPassenger(Long userId, PassengerRequest request);

}
