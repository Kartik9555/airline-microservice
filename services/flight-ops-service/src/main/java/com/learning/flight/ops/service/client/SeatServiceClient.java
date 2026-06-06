package com.learning.flight.ops.service.client;

import com.learning.common.enums.CabinClassType;
import com.learning.common.payload.response.CabinClassResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "seat-service")
public interface SeatServiceClient {

    @GetMapping("/api/v1/cabin-classes/{name}/aircrat/{aircraftId}")
    CabinClassResponse getCabinClassByAircraftIdAndName(@PathVariable("aircraftId") Long aircraftId, @PathVariable("name") CabinClassType name);
}
