package com.learning.common.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Baggage {

    @PositiveOrZero(message = "Max Weight must be zero or positive ")
    private Double maxWeight;

    @PositiveOrZero(message = "Number of pieces must be zero or positive")
    private Integer pieces;

    @PositiveOrZero(message = "Weight per piece must be zero or positive")
    private Double weightPerPiece;

    @PositiveOrZero(message = "Max dimension must be zero or positive")
    private Integer maxDimension;
}
