package com.learning.payment.service.mapper;

import com.learning.common.enums.PaymentStatus;
import com.learning.common.payload.dto.PaymentDTO;
import com.learning.common.payload.request.PaymentInitiateRequest;
import com.learning.common.payload.response.PaymentInitiateResponse;
import com.learning.payment.service.model.Payment;

public class PaymentMapper {

    public static PaymentInitiateResponse toPayment(Payment payment) {
        if (payment == null) return null;
        return PaymentInitiateResponse.builder()
                .paymentId(payment.getId())
                .provider(payment.getProvider())
                .transactionId(payment.getTransactionId())
                .providerPaymentId(payment.getProviderPaymentId())
                .amount(payment.getAmount())
                .description(payment.getDescription())
                .success(true)
                .message("Payment initiated successfully")
                .build();
    }

    public static PaymentDTO toPaymentDTO(Payment payment) {
        if (payment == null) return null;
        return PaymentDTO.builder()
                .id(payment.getId())
                .userId(payment.getUserId())
                .bookingId(payment.getBookingId())
                .paymentStatus(payment.getStatus())
                .provider(payment.getProvider())
                .amount(payment.getAmount())
                .transactionId(payment.getTransactionId())
                .providerPaymentId(payment.getProviderPaymentId())
                .description(payment.getDescription())
                .failureReason(payment.getFailureReason())
                .initiatedAt(payment.getCreatedAt())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .completedAt(payment.getPaidAt())
                .providerOrderId(payment.getOrderId())
                .build();
    }

    public static Payment toPayment(PaymentInitiateRequest request) {
        if (request == null) return null;
        return Payment.builder()
                .userId(request.getUserId())
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .provider(request.getProvider())
                .status(PaymentStatus.PENDING)
                .description(request.getDescription())
                .build();
    }
}
