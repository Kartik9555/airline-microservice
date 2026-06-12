package com.learning.seat.service.service.impl;

import com.learning.common.enums.CabinClassType;
import com.learning.common.enums.SeatType;
import com.learning.common.payload.request.SeatRequest;
import com.learning.common.payload.response.SeatResponse;
import com.learning.seat.service.mapper.SeatMapper;
import com.learning.seat.service.model.CabinClass;
import com.learning.seat.service.model.Seat;
import com.learning.seat.service.model.SeatMap;
import com.learning.seat.service.repository.SeatMapRepository;
import com.learning.seat.service.repository.SeatRepository;
import com.learning.seat.service.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.learning.common.enums.SeatType.AISLE;
import static com.learning.common.enums.SeatType.MIDDLE;
import static com.learning.common.enums.SeatType.WINDOW;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapRepository seatMapRepository;

    @Override
    @Transactional
    public void generateSeat(Long seatMapId) throws Exception {
        if(seatRepository.existsBySeatMapId(seatMapId)) {
            throw new Exception("Seats with Seat Map id: " + seatMapId + "already exists");
        }
        final SeatMap seatMap = seatMapRepository.findById(seatMapId)
                .orElseThrow(() -> new Exception("Seats with Seat Map id: " + seatMapId + " not found"));

        final CabinClass cabinClass = seatMap.getCabinClass();
        int leftSeatsPerRow = seatMap.getLeftSeatsPerRow();
        int rightSeatsPerRow = seatMap.getRightSeatsPerRow();
        int totalRows = seatMap.getTotalRows();
        int seatsPerRow = leftSeatsPerRow + rightSeatsPerRow;

        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= totalRows; row++) {
            for(int col = 0; col < seatsPerRow; col++) {
                String seatNumber = row + getSeatLetter(col);
                SeatType seatType = getSeatType(col, leftSeatsPerRow, rightSeatsPerRow);
                Seat seat = Seat.builder()
                        .seatNumber(seatNumber)
                        .seatRow(row)
                        .columnLetter(getSeatLetter(col).charAt(0))
                        .seatType(seatType)
                        .seatMap(seatMap)
                        .cabinClass(seatMap.getCabinClass())
                        .hasExtraLegRoom(hasExtraLegRoom(cabinClass.getName()))
                        .isWheelChairAccessible(isWheelChairAccessible(cabinClass.getName()))
                        .isPremiumSeat(isPremiumSeat(cabinClass.getName()))
                        .hasPowerOutlet(isPremiumSeat(cabinClass.getName()))
                        .hasTvScreen(isPremiumSeat(cabinClass.getName()))
                        .hasExtraWidth(hasExtraWidth(cabinClass.getName()))
                        .seatPitch(cabinClass.getTypicalSeatPitch())
                        .seatWidth(cabinClass.getTypicalSeatWidth())
                        .build();
                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);
    }

    private SeatType getSeatType(int col, int leftSeatsPerRow, int rightSeatsPerRow) {
        int totalSeatsPerRow = leftSeatsPerRow + rightSeatsPerRow;
        if (col == 0 || col == totalSeatsPerRow-1) return WINDOW;
        if(col == leftSeatsPerRow-1) return AISLE;
        if(col == rightSeatsPerRow-1) return AISLE;
        return MIDDLE;
    }

    private String getSeatLetter(int col) {
        StringBuilder sb = new StringBuilder();
        col++; // convert 0-based to 1-based
        while (col > 0) {
            col--;
            sb.insert(0, (char) ('A' + (col % 26)));
            col /= 26;
        }
        return sb.toString();
    }

    private boolean isPremiumSeat(CabinClassType cabinClass) {
        return switch (cabinClass) {
            case BUSINESS, FIRST, PREMIUM_ECONOMY ->  true;
            case ECONOMY -> false;
        };
    }

    private boolean isWheelChairAccessible(CabinClassType cabinClass) {
        return switch (cabinClass) {
            case BUSINESS, FIRST, PREMIUM_ECONOMY ->  true;
            case ECONOMY -> false;
        };
    }

    private boolean hasExtraWidth(CabinClassType cabinClass) {
        return switch (cabinClass) {
            case BUSINESS, FIRST, PREMIUM_ECONOMY ->  true;
            case ECONOMY -> false;
        };
    }

    private boolean hasExtraLegRoom(CabinClassType cabinClass) {
        return switch (cabinClass) {
            case BUSINESS, FIRST ->  true;
            case  PREMIUM_ECONOMY, ECONOMY -> false;
        };
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatResponse> getAll() {
        return seatRepository.findAll()
                .stream()
                .map(SeatMapper::toSeat)
                .toList();
    }

    @Override
    @Transactional
    public SeatResponse updateSeats(Long seatId, SeatRequest request) throws Exception {
        final Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new Exception("Seat with id " + seatId + "not found"));
        return SeatMapper.toSeat(seat);
    }
}
