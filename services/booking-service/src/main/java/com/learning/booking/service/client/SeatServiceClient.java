package com.learning.booking.service.client;

import com.learning.common.payload.response.SeatInstanceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "seat-service")
public interface SeatServiceClient {

    @GetMapping("/api/v1/seat-instances/price/total")
    Double calculatePrice(@RequestBody List<Long> seatInstancesId);

    @GetMapping("/api/v1/seat-instances/all")
    List<SeatInstanceResponse> getAllSeatInstancesByIds(@RequestBody List<Long> seatInstancesId);
}
