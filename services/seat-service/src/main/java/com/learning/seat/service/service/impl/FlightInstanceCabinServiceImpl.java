package com.learning.seat.service.service.impl;

import com.learning.common.enums.SeatAvailabilityStatus;
import com.learning.common.enums.SeatType;
import com.learning.common.payload.request.FlightInstanceCabinRequest;
import com.learning.common.payload.response.FlightInstanceCabinResponse;
import com.learning.seat.service.mapper.FlightInstanceCabinMapper;
import com.learning.seat.service.model.CabinClass;
import com.learning.seat.service.model.FlightInstanceCabin;
import com.learning.seat.service.model.SeatInstance;
import com.learning.seat.service.model.SeatMap;
import com.learning.seat.service.repository.CabinClassRepository;
import com.learning.seat.service.repository.FlightInstanceCabinRepository;
import com.learning.seat.service.repository.SeatInstanceRepository;
import com.learning.seat.service.repository.SeatMapRepository;
import com.learning.seat.service.service.FlightInstanceCabinService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightInstanceCabinServiceImpl implements FlightInstanceCabinService {

    private final FlightInstanceCabinRepository flightInstanceCabinRepository;
    private final CabinClassRepository cabinClassRepository;
    private final SeatMapRepository seatMapRepository;
    private final SeatInstanceRepository seatInstanceRepository;

    @Override
    @Transactional
    public FlightInstanceCabinResponse createFlightInstanceCabin(FlightInstanceCabinRequest request) throws Exception {
        final CabinClass cabinClass = cabinClassRepository.findById(request.getCabinClassId())
                .orElseThrow(() -> new Exception("Cabin class not found"));

        final SeatMap seatMap = seatMapRepository.findByCabinClassId(cabinClass.getId())
                .orElseThrow(() -> new Exception("Seat Map not found"));

        if(seatMap.getSeats() == null || seatMap.getSeats().isEmpty()) {
            throw new Exception("No seats found in Seat Map");
        }
        final int totalSeats = seatMap.getSeats().size();
        final FlightInstanceCabin  flightInstanceCabin = FlightInstanceCabin.builder()
                .flightInstanceId(request.getFlightInstanceId())
                .cabinClass(cabinClass)
                .totalSeats(totalSeats)
                .bookedSeats(0)
                .build();

        final FlightInstanceCabin saved = flightInstanceCabinRepository.save(flightInstanceCabin);
        List<SeatInstance> seatInstances = seatMap.getSeats()
                .stream()
                .map(seat -> SeatInstance.builder()
                        .flightId(request.getFlightId())
                        .flightInstanceCabin(saved)
                        .flightInstanceId(request.getFlightInstanceId())
                        .seat(seat)
                        .status(SeatAvailabilityStatus.AVAILABLE)
                        .isBooked(false)
                        .isAvailable(true)
                        .premiumSurcharge(getPremiumSurcharge(seat.getSeatType(), 1000.0, 500.0))
                        .build())
                .toList();
        seatInstanceRepository.saveAll(seatInstances);
        saved.setSeats(seatInstances);
        return FlightInstanceCabinMapper.toFlightInstanceCabin(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public FlightInstanceCabinResponse getFlightInstanceCabinById(Long id) throws Exception {
        final FlightInstanceCabin flightInstanceCabin = flightInstanceCabinRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight Instance Cabin not found with id: " + id));
        return FlightInstanceCabinMapper.toFlightInstanceCabin(flightInstanceCabin);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FlightInstanceCabinResponse> getByFlightInstanceId(Long flightInstanceId, Pageable pageable) throws Exception {
        return flightInstanceCabinRepository.findByFlightInstanceId(flightInstanceId, pageable)
                .map(FlightInstanceCabinMapper::toFlightInstanceCabin);
    }

    @Override
    @Transactional(readOnly = true)
    public FlightInstanceCabinResponse getByFlightInstanceIdAndCabinClassId(Long flightInstanceId, Long cabinClassId) throws Exception {
        final FlightInstanceCabin flightInstanceCabin = flightInstanceCabinRepository.findByFlightInstanceIdAndCabinClassId(flightInstanceId, cabinClassId)
                .orElseThrow(() -> new Exception("Flight instance Cabin not found"));
        return FlightInstanceCabinMapper.toFlightInstanceCabin(flightInstanceCabin);
    }

    @Override
    @Transactional
    public FlightInstanceCabinResponse updateFlightInstanceCabin(Long id, FlightInstanceCabinRequest request) throws Exception {
        final FlightInstanceCabin flightInstanceCabin = flightInstanceCabinRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight Instance Cabin not found with id: " + id));

        if(request.getCabinClassId() != null) {
            final CabinClass cabinClass = cabinClassRepository.findById(request.getCabinClassId())
                    .orElseThrow(() -> new Exception("Cabin Class not found"));
            flightInstanceCabin.setCabinClass(cabinClass);
        }
        final FlightInstanceCabin saved = flightInstanceCabinRepository.save(flightInstanceCabin);
        return FlightInstanceCabinMapper.toFlightInstanceCabin(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteFlightInstanceCabin(Long id) throws Exception {
        final FlightInstanceCabin flightInstanceCabin = flightInstanceCabinRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight Instance Cabin not found with id: " + id));
        flightInstanceCabinRepository.delete(flightInstanceCabin);
    }

    private Double getPremiumSurcharge(SeatType seatType, Double windowSurcharge, Double aisleSurcharge) {
        if (seatType == null) return 0.0;
        return switch (seatType) {
            case AISLE -> aisleSurcharge;
            case WINDOW -> windowSurcharge;
            default -> 0.0;
        };
    }
}
