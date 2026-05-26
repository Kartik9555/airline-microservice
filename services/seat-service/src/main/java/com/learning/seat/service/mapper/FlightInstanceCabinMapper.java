package com.learning.seat.service.mapper;

import com.learning.common.payload.response.FlightInstanceCabinResponse;
import com.learning.seat.service.model.FlightInstanceCabin;

public class FlightInstanceCabinMapper {
    public static FlightInstanceCabinResponse toFlightInstanceCabin(final FlightInstanceCabin flightInstanceCabin) {
        if (flightInstanceCabin == null) return null;
        return FlightInstanceCabinResponse.builder()
                .id(flightInstanceCabin.getId())
                .flightInstanceId(flightInstanceCabin.getFlightInstanceId())
                .cabinClass(CabinClassMapper.toCabinClass(flightInstanceCabin.getCabinClass()))
                .totalSeats(flightInstanceCabin.getTotalSeats())
                .availableSeats(flightInstanceCabin.getAvailableSeats())
                .cabinClassType(flightInstanceCabin.getCabinClass() != null ? flightInstanceCabin.getCabinClass().getName() : null)
                .bookedSeats(flightInstanceCabin.getBookedSeats())
                .seatMap(SeatMapMapper.toSeatMap(flightInstanceCabin.getCabinClass() != null ? flightInstanceCabin.getCabinClass().getSeatMap() : null))
                .seats(flightInstanceCabin.getSeats() != null ? flightInstanceCabin.getSeats().stream().map(SeatInstanceMapper::toSeatInstance).toList() : null)
                .build();
    }
}
