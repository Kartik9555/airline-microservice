package com.learning.common.payload.response;

import com.learning.common.embeddable.Baggage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaggagePolicyResponse {
    private Long id;
    private String name;
    private String description;
    private Baggage cabinBaggage;
    private Baggage checkingBaggage;
    private Integer freeCheckedBagsAllowance;
    private Boolean priorityBaggage;
    private Boolean extraBaggageAllowance;
}
