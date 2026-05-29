package com.learning.booking.service.service.outbound;

import com.learning.booking.service.client.PaymentClient;
import com.learning.common.payload.dto.PaymentDTO;
import com.learning.common.payload.request.PaymentInitiateRequest;
import com.learning.common.payload.response.PaymentInitiateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentOutboundService {

    private final PaymentClient paymentClient;

    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) {
        return paymentClient.initiatePayment(request);
    }

    public PaymentDTO getPaymentById(Long id) {
        return paymentClient.getPaymentById(id);
    }
}
