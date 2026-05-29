package com.learning.common.payload.response;

import com.learning.common.enums.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInitiateResponse {
    private Long paymentId;
    private PaymentProvider provider;
    private String transactionId;
    private String providerPaymentId;
    private Double amount;
    private String description;
    private String checkoutUrl;
    private String message;
    private Boolean success;
}
