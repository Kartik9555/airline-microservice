package com.learning.booking.service.service.impl;

import com.learning.booking.service.mapper.PassengerMapper;
import com.learning.booking.service.model.Passenger;
import com.learning.booking.service.repository.PassengerRepository;
import com.learning.booking.service.service.PassengerService;
import com.learning.common.payload.request.PassengerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    @Override
    @Transactional
    public Passenger createPassenger(Long userId, PassengerRequest request) {
        final Passenger passenger = PassengerMapper.toPassenger(request);
        passenger.setPrimaryUserId(userId);
        return passengerRepository.save(passenger);
    }
}
