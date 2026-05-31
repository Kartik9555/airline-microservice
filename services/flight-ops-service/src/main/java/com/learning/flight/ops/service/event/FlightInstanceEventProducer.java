package com.learning.flight.ops.service.event;

import com.learning.common.event.FlightInstanceCreatedEvent;
import com.learning.flight.ops.service.model.FlightInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightInstanceEventProducer {

    private final KafkaTemplate<String, FlightInstanceCreatedEvent> kafkaTemplate;

    public void sendFlightInstanceCreatedEvent(FlightInstance flightInstance) {
        FlightInstanceCreatedEvent event = FlightInstanceCreatedEvent.builder()
                .flightInstanceId(flightInstance.getId())
                .flightId(flightInstance.getFlight().getId())
                .aircraftId(flightInstance.getFlight().getAircraftId())
                .build();
        kafkaTemplate.send("flight_instance_created", event);
    }
}
