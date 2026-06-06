package com.learning.booking.service.client;

import com.learning.common.payload.dto.PaymentDTO;
import com.learning.common.payload.request.PaymentInitiateRequest;
import com.learning.common.payload.request.PaymentVerifyRequest;
import com.learning.common.payload.response.PaymentInitiateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/api/v1/payments/initiate")
    PaymentInitiateResponse initiatePayment(@RequestBody PaymentInitiateRequest request);

    @PostMapping("/api/v1/payments/verify")
    PaymentDTO verifyPayment(@RequestBody PaymentVerifyRequest request);

    @GetMapping("/api/v1/payments/{id}")
    PaymentDTO getPaymentById(@PathVariable("id") Long id);
}
