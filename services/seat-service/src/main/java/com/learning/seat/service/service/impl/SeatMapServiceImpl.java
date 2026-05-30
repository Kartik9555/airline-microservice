package com.learning.seat.service.service.impl;

import com.learning.common.payload.request.SeatMapRequest;
import com.learning.common.payload.response.AirlineResponse;
import com.learning.common.payload.response.SeatMapResponse;
import com.learning.seat.service.mapper.SeatMapMapper;
import com.learning.seat.service.model.CabinClass;
import com.learning.seat.service.model.SeatMap;
import com.learning.seat.service.repository.CabinClassRepository;
import com.learning.seat.service.repository.SeatMapRepository;
import com.learning.seat.service.service.SeatMapService;
import com.learning.seat.service.service.SeatService;
import com.learning.seat.service.service.outbound.AirlineOutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatMapServiceImpl implements SeatMapService {

    private final SeatMapRepository seatMapRepository;
    private final CabinClassRepository cabinClassRepository;
    private final SeatService seatService;
    private final AirlineOutboundService airlineService;

    @Override
    public SeatMapResponse getSeatMapById(Long id) throws Exception {
        final SeatMap seatMap = seatMapRepository.findById(id)
                .orElseThrow( () -> new Exception("Seat Map with id " + id + " not found."));
        return SeatMapMapper.toSeatMap(seatMap);
    }

    @Override
    public SeatMapResponse getSeatMapByCabinClass(Long cabinClassId) throws Exception {
        final SeatMap seatMap = seatMapRepository.findByCabinClassId(cabinClassId)
                .orElseThrow( () -> new Exception("Seat Map with Cabin Class id " + cabinClassId + " not found."));
        return SeatMapMapper.toSeatMap(seatMap);
    }

    @Override
    public SeatMapResponse createSeatMap(Long userId, SeatMapRequest request) throws Exception {
        final CabinClass cabinClass = cabinClassRepository.findById(request.getCabinClassId())
                .orElseThrow( () -> new Exception("Cabin Clas with id " + request.getCabinClassId() + " not found"));

        final AirlineResponse airline = airlineService.getAirlineByUserId(userId);
        if(seatMapRepository.existsByAirlineIdAndCabinClassIdAndName(airline.getId(), cabinClass.getId(), request.getName())) {
            throw new Exception("Seat Map already exists");
        }

        final SeatMap seatMap = SeatMapMapper.toSeatMap(request, cabinClass);
        seatMap.setAirlineId(airline.getId());

        final SeatMap saved = seatMapRepository.save(seatMap);
        seatService.generateSeat(saved.getId());
        return SeatMapMapper.toSeatMap(saved);
    }

    @Override
    public SeatMapResponse updateSeatMap(Long id, SeatMapRequest request) throws Exception {
        final SeatMap seatMap = seatMapRepository.findById(id)
                .orElseThrow( () -> new Exception("Seat Map with id " + id + " not found."));
        SeatMapMapper.toSeatMap(request, seatMap);
        return SeatMapMapper.toSeatMap(seatMapRepository.save(seatMap));
    }

    @Override
    public void deleteSeatMap(Long id) throws Exception {
        final SeatMap seatMap = seatMapRepository.findById(id)
                .orElseThrow( () -> new Exception("Seat Map with id " + id + " not found."));
        seatMapRepository.delete(seatMap);
    }
}
