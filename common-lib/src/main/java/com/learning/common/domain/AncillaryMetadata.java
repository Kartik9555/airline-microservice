package com.learning.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AncillaryMetadata {
    @Embedded
    private BaggageMetadata baggage;
    private String protectionSummary;
    private String specialServiceDetails;
    private String upgradeDetails;
}
