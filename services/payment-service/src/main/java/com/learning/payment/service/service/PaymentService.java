package com.learning.payment.service.service;

import com.learning.common.payload.dto.PaymentDTO;
import com.learning.common.payload.request.PaymentInitiateRequest;
import com.learning.common.payload.request.PaymentVerifyRequest;
import com.learning.common.payload.response.PaymentInitiateResponse;
import com.razorpay.RazorpayException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException;
    PaymentDTO verifyPayment(PaymentVerifyRequest request) throws Exception;
    Page<PaymentDTO> getAllPayments(Pageable pageable);
    Map<Long, PaymentDTO> getPaymentByBookingIds(List<Long> bookingIds);
    PaymentDTO getPaymentById(Long paymentId) throws Exception;
}
