package com.learning.seat.service.mapper;

import com.learning.common.payload.request.SeatMapRequest;
import com.learning.common.payload.response.SeatMapResponse;
import com.learning.seat.service.model.CabinClass;
import com.learning.seat.service.model.SeatMap;

public class SeatMapMapper {

    public static SeatMapResponse toSeatMap(SeatMap seatMap) {
        if(seatMap == null) return null;
        return SeatMapResponse.builder()
                .id(seatMap.getId())
                .name(seatMap.getName())
                .totalRows(seatMap.getTotalRows())
                .airlineId(seatMap.getAirlineId())
                .cabinClassId(seatMap.getCabinClass() != null ? seatMap.getCabinClass().getId() : null)
                .cabinClassCode(seatMap.getCabinClass() != null ? seatMap.getCabinClass().getCode() : null)
                .cabinClassName(seatMap.getCabinClass() != null ? seatMap.getCabinClass().getName().name() : null)
//                .totalSeats()
//                .availableSeats()
//                .occupiedSeats()
//                .seats()
//                .windowSeats()
//                .aisleSeats()
//                .middleSeats()
//                .premiumSeats()
//                .emergencyExitSeats()
                .leftSeatsPerRow(seatMap.getRightSeatsPerRow())
                .rightSeatsPerRow(seatMap.getRightSeatsPerRow())
                .build();
    }

    public static SeatMap toSeatMap(SeatMapRequest request, CabinClass cabinClass) {
        if(request == null) return null;
        return SeatMap.builder()
                .name(request.getName())
                .totalRows(request.getTotalRows())
                .rightSeatsPerRow(request.getRightSeatsPerRow())
                .leftSeatsPerRow(request.getLeftSeatsPerRow())
                .cabinClass(cabinClass)
                .build();
    }

    public static void toSeatMap(SeatMapRequest request, SeatMap seatMap) {
        if(request == null || seatMap == null) return;
        seatMap.setName(request.getName());
        seatMap.setTotalRows(request.getTotalRows());
        seatMap.setRightSeatsPerRow(request.getRightSeatsPerRow());
        seatMap.setLeftSeatsPerRow(request.getLeftSeatsPerRow());
    }
}
