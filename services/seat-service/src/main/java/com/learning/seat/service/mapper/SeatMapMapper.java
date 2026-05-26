package com.learning.seat.service.mapper;

import com.learning.common.payload.request.SeatMapRequest;
import com.learning.common.payload.response.SeatMapResponse;
import com.learning.seat.service.model.CabinClass;
import com.learning.seat.service.model.Seat;
import com.learning.seat.service.model.SeatMap;

import java.util.List;
import java.util.function.Predicate;

import static com.learning.common.enums.SeatType.AISLE;
import static com.learning.common.enums.SeatType.MIDDLE;
import static com.learning.common.enums.SeatType.WINDOW;
import static java.lang.Boolean.TRUE;

public class SeatMapMapper {

    private static final Predicate<Seat> IS_WINDOW_SEAT = seat -> seat.getSeatType().equals(WINDOW);
    private static final Predicate<Seat> IS_AISLE_SEAT = seat -> seat.getSeatType().equals(AISLE);
    private static final Predicate<Seat> IS_MIDDLE_SEAT = seat -> seat.getSeatType().equals(MIDDLE);
    private static final Predicate<Seat> IS_AVAILABLE_SEAT = seat -> TRUE.equals(seat.getIsAvailable())
            && TRUE.equals(seat.getIsActive()) && !TRUE.equals(seat.getIsBlocked());
    private static final Predicate<Seat> IS_PREMIUM_SEAT = seat -> TRUE.equals(seat.getHasExtraLegRoom())
            && TRUE.equals(seat.getIsEmergencyExit()) && !TRUE.equals(seat.getHasExtraWidth());
    private static final Predicate<Seat> IS_EMERGENCY_EXIT_SEAT = seat -> TRUE.equals(seat.getIsEmergencyExit());

    public static SeatMapResponse toSeatMap(SeatMap seatMap) {
        if(seatMap == null) return null;
        final List<Seat> seats = seatMap.getSeats();
        final int totalSeats = seats != null ? seats.size() : 0;
        final int availableSeats = seats != null ? getSeatCount(seats, IS_AVAILABLE_SEAT) : 0;
        return SeatMapResponse.builder()
                .id(seatMap.getId())
                .name(seatMap.getName())
                .totalRows(seatMap.getTotalRows())
                .airlineId(seatMap.getAirlineId())
                .cabinClassId(seatMap.getCabinClass() != null ? seatMap.getCabinClass().getId() : null)
                .cabinClassCode(seatMap.getCabinClass() != null ? seatMap.getCabinClass().getCode() : null)
                .cabinClassName(seatMap.getCabinClass() != null ? seatMap.getCabinClass().getName().name() : null)
                .totalSeats(totalSeats)
                .availableSeats(seats != null ? getSeatCount(seats, IS_AVAILABLE_SEAT) : 0)
                .occupiedSeats(totalSeats - availableSeats)
                .seats(seats != null ? seats.stream().map(SeatMapper::toSeat).toList() : null)
                .windowSeats(seats != null ? getSeatCount(seats, IS_WINDOW_SEAT) : 0)
                .aisleSeats(seats != null ? getSeatCount(seats, IS_AISLE_SEAT) : 0)
                .middleSeats(seats != null ? getSeatCount(seats, IS_MIDDLE_SEAT) : 0)
                .premiumSeats(seats != null ? getSeatCount(seats, IS_PREMIUM_SEAT) : 0)
                .emergencyExitSeats(seats != null ? getSeatCount(seats, IS_EMERGENCY_EXIT_SEAT) : 0)
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

    private static int getSeatCount(List<Seat> seats, Predicate<Seat> seatPredicate) {
        return Math.toIntExact(seats.stream()
                .filter(seatPredicate)
                .count());
    }
}
