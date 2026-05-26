package com.learning.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaggageMetadata {
    private Integer weight;
    private Integer pieces;
    private String unit;
    private String category;
    private String dimensions;
    private String notes;
}
