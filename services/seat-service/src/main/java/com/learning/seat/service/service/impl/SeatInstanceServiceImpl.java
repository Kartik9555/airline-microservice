package com.learning.seat.service.service.impl;

import com.learning.common.enums.SeatAvailabilityStatus;
import com.learning.common.payload.response.SeatInstanceResponse;
import com.learning.seat.service.mapper.SeatInstanceMapper;
import com.learning.seat.service.model.SeatInstance;
import com.learning.seat.service.repository.SeatInstanceRepository;
import com.learning.seat.service.service.SeatInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatInstanceServiceImpl implements SeatInstanceService {

    private final SeatInstanceRepository seatInstanceRepository;

    @Override
    public Double calculateSeatPrice(List<Long> seatInstanceIds) {
        final List<SeatInstance> seatInstances = seatInstanceRepository.findAllById(seatInstanceIds);
        double price = 0.0;
        for (SeatInstance seatInstance : seatInstances) {
            double seatPremium = seatInstance.getPremiumSurcharge() != null ? seatInstance.getPremiumSurcharge() : 0.0;
            price += seatPremium;
        }
        return price;
    }

    @Override
    public List<SeatInstanceResponse> getAllSeatInstancesByIds(List<Long> seatInstanceIds) {
        return seatInstanceRepository.findAllById(seatInstanceIds)
                .stream()
                .map(SeatInstanceMapper::toSeatInstance)
                .toList();
    }

    @Override
    public SeatInstanceResponse updateSeatInstanceStatus(Long id, SeatAvailabilityStatus status) {
        final SeatInstance seatInstance = seatInstanceRepository.findById(id).orElse(null);
        if (seatInstance != null) {
            seatInstance.setStatus(status);
            return SeatInstanceMapper.toSeatInstance(seatInstanceRepository.save(seatInstance));
        }
        return null;
    }
}
