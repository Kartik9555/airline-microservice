package com.learning.seat.service.service.impl;

import com.learning.common.enums.SeatType;
import com.learning.common.payload.request.SeatRequest;
import com.learning.common.payload.response.SeatMapResponse;
import com.learning.common.payload.response.SeatResponse;
import com.learning.seat.service.mapper.SeatMapper;
import com.learning.seat.service.model.Seat;
import com.learning.seat.service.model.SeatMap;
import com.learning.seat.service.repository.SeatMapRepository;
import com.learning.seat.service.repository.SeatRepository;
import com.learning.seat.service.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void generateSeat(Long seatMapId) throws Exception {
        if(seatRepository.existsBySeatMapId(seatMapId)) {
            throw new Exception("Seats with Seat Map id: " + seatMapId + "already exists");
        }
        final SeatMap seatMap = seatMapRepository.findById(seatMapId)
                .orElseThrow(() -> new Exception("Seats with Seat Map id: " + seatMapId + " not found"));

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
        while (col > 0) {
            sb.insert(0, (char) ('A' + col % 26));
            col /= 26-1;
        }
        return sb.toString();
    }

    @Override
    public List<SeatResponse> getAll() {
        return seatRepository.findAll()
                .stream()
                .map(SeatMapper::toSeat)
                .toList();
    }

    @Override
    public SeatMapResponse updateSeats(Long seatId, SeatRequest request) throws Exception {
        seatRepository.findById(seatId)
                .orElseThrow(() -> new Exception("Seat with id " + seatId + "not found"));
        return null;
    }
}
