package com.learning.seat.service.event;

import com.learning.common.enums.SeatAvailabilityStatus;
import com.learning.common.enums.SeatType;
import com.learning.common.event.FlightInstanceCreatedEvent;
import com.learning.seat.service.model.CabinClass;
import com.learning.seat.service.model.FlightInstanceCabin;
import com.learning.seat.service.model.Seat;
import com.learning.seat.service.model.SeatInstance;
import com.learning.seat.service.repository.CabinClassRepository;
import com.learning.seat.service.repository.FlightInstanceCabinRepository;
import com.learning.seat.service.repository.SeatInstanceRepository;
import com.learning.seat.service.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightInstanceEventConsumer {

    private final CabinClassRepository cabinClassRepository;
    private final SeatRepository seatRepository;
    private final FlightInstanceCabinRepository flightInstanceCabinRepository;
    private final SeatInstanceRepository seatInstanceRepository;

    @KafkaListener(topics = "flight_instance_created", groupId = "seat-service-group")
    @Transactional
    public void handleFlightInstanceCreatedEvent(FlightInstanceCreatedEvent event) {
        List<CabinClass> cabinClasses = cabinClassRepository.getByAircraftId(event.getAircraftId());
        int totalSeatInstances = 0;
        for (CabinClass cabinClass : cabinClasses) {
            final List<Seat> seats = cabinClass.getSeatMap() != null ?
                    seatRepository.findBySeatMapId(cabinClass.getSeatMap().getId())
                    : List.of();
            final FlightInstanceCabin flightInstanceCabin = FlightInstanceCabin.builder()
                    .flightInstanceId(event.getFlightInstanceId())
                    .totalSeats(seats.size())
                    .cabinClass(cabinClass)
                    .bookedSeats(0)
                    .build();
            final FlightInstanceCabin saved = flightInstanceCabinRepository.save(flightInstanceCabin);

            final List<SeatInstance> seatInstances = seats.stream()
                    .map(seat -> SeatInstance.builder()
                            .flightId(event.getFlightId())
                            .flightInstanceId(event.getFlightInstanceId())
                            .seat(seat)
                            .flightInstanceCabin(saved)
                            .status(SeatAvailabilityStatus.AVAILABLE)
                            .isAvailable(true)
                            .isBooked(false)
                            .premiumSurcharge(getPremiumSurcharge(seat.getSeatType(), 1000.0, 500.0))
                            .build())
                    .toList();
            seatInstanceRepository.saveAll(seatInstances);
            totalSeatInstances += seatInstances.size();
        }
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
