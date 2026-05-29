package com.learning.common.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentLinkResponse {
    private Long id;

    @JsonProperty("payment_link_url")
    private String paymentLinkUrl;

    @JsonProperty("payment_link_id")
    private String paymentLinkId;;
}
