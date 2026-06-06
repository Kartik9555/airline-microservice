package com.learning.booking.service.client;

import com.learning.common.payload.response.SeatInstanceResponse;
import jakarta.ws.rs.GET;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "seat-service")
public interface SeatServiceClient {

    @GetMapping("/api/v1/seat-instances/price/total")
    Double calculatePrice(@RequestParam("seatInstancesId") List<Long> seatInstancesId);

    @GetMapping("/api/v1/seat-instances/all")
    List<SeatInstanceResponse> getAllSeatInstancesByIds(@RequestParam("seatInstancesId") List<Long> seatInstancesId);
}
