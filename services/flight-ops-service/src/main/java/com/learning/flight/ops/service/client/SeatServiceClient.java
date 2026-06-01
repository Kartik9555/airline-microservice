package com.learning.flight.ops.service.client;

import com.learning.common.enums.CabinClassType;
import com.learning.common.payload.response.CabinClassResponse;
import com.learning.common.payload.response.SeatInstanceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "seat-service")
public interface SeatServiceClient {

    @GetMapping("/api/v1/cabin-classes/{name}/aircrat/{aircraftId}")
    CabinClassResponse getCabinClassByAircraftIdAndName(@PathVariable Long aircraftId, @PathVariable CabinClassType name);
}
