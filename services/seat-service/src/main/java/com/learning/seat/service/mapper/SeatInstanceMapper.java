package com.learning.seat.service.mapper;

import com.learning.common.enums.SeatAvailabilityStatus;
import com.learning.common.payload.response.SeatInstanceResponse;
import com.learning.seat.service.model.SeatInstance;

public class SeatInstanceMapper {
    public static SeatInstanceResponse toSeatInstance(SeatInstance seatInstance) {
        if (seatInstance == null) return null;
        return SeatInstanceResponse.builder()
                .id(seatInstance.getId())
                .flightId(seatInstance.getFlightId())
                .seatId(seatInstance.getSeat().getId())
                .seatNumber(seatInstance.getSeat() != null ? seatInstance.getSeat().getSeatNumber() : null)
                .seatType(seatInstance.getSeat() != null ? seatInstance.getSeat().getSeatType().name() : null)
                .seatPosition(seatInstance.getSeat() != null ? seatInstance.getSeat().getFullPosition() : null)
                .seat(SeatMapper.toSeat(seatInstance.getSeat()))
                .status(seatInstance.getStatus())
                .flightInstanceId(seatInstance.getFlightInstanceId())
                .isBooked(seatInstance.getIsBooked())
                .flightCabinId(seatInstance.getFlightInstanceCabin() != null ? seatInstance.getFlightInstanceCabin().getId() : null)
                .flightCabinClassType(seatInstance.getFlightInstanceCabin() != null ? seatInstance.getFlightInstanceCabin().getCabinClass().getName() : null)
                .fare(seatInstance.getFare())
                .createdAt(seatInstance.getCreatedAt())
                .updatedAt(seatInstance.getUpdatedAt())
                .isAvailable(seatInstance.getIsAvailable())
                .isOccupied(seatInstance.getStatus() == SeatAvailabilityStatus.OCCUPIED)
                .build();
    }
}
