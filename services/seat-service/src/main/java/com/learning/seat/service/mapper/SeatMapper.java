package com.learning.seat.service.mapper;

import com.learning.common.payload.response.SeatMapResponse;
import com.learning.common.payload.response.SeatResponse;
import com.learning.seat.service.model.Seat;

import java.time.LocalDateTime;

public class SeatMapper {

    public static SeatResponse toSeat(Seat seat) {
        if(seat == null) return null;
        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatRow(seat.getSeatRow())
                .seatType(seat.getSeatType())
                .isAvailable(seat.getIsAvailable())
                .isBlocked(seat.getIsBlocked())
                .isEmergencyExit(seat.getIsEmergencyExit())
                .isActive(seat.getIsActive())
                .basePrice(seat.getBasePrice())
                .premiumSurcharge(seat.getPremiumSurcharge())
                .totalPrice(seat.getTotalPrice())
                .hasExtraLegRoom(seat.getHasExtraLegRoom())
                .hasBassinet(seat.getHasBassinet())
                .isNearLavatory(seat.getIsNearLavatory())
                .isNearGalley(seat.getIsNearGalley())
                .hasPowerOutlet(seat.getHasPowerOutlet())
                .hasTvScreen(seat.getHasTvScreen())
                .isWheelChairAccessible(seat.getIsWheelChairAccessible())
                .hasExtraWidth(seat.getHasExtraWidth())
                .seatPitch(seat.getSeatPitch())
                .seatWidth(seat.getSeatWidth())
                .reclineAngle(seat.getReclineAngle())
                .columnLetter(seat.getColumnLetter())
                .seatMapId(seat.getSeatMap().getId())
                .seatMapName(seat.getSeatMap().getName())
                .cabinClassId(seat.getCabinClass().getId())
                .cabinClassName(seat.getCabinClass().getName().name())
                .createdAt(seat.getCreatedAt())
                .updatedAt(seat.getUpdatedAt())
                .createdBy(seat.getCreatedBy())
                .updatedBy(seat.getUpdatedBy())
                .isBookable(seat.isBookable())
                .fullPosition(seat.getFullPosition())
                .isPremiumSeat(seat.getIsPremiumSeat())
                .build();
    }
}
