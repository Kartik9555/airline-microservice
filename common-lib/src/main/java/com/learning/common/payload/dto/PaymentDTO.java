package com.learning.common.payload.dto;

import com.learning.common.enums.PaymentProvider;
import com.learning.common.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long bookingId;
    private PaymentStatus paymentStatus;
    private PaymentProvider provider;
    private Double amount;
    private String transactionId;
    private String providerPaymentId;
    private String providerOrderId;
    private String providerSignature;
    private String paymentMethod;
    private String description;
    private String failureReason;
    private Integer retryCount;
    private Instant initiatedAt;
    private Instant completedAt;
    private Boolean notificationSent;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
