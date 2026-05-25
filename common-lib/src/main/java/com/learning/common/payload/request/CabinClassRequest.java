package com.learning.common.payload.request;

import com.learning.common.enums.CabinClassType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabinClassRequest {

    @NotBlank(message = "Cabin Class Name is mandatory")
    private CabinClassType name;

    @NotBlank(message = "Code is mandatory")
    private String code;

    private String description;

    @NotNull(message = "Aircraft ID is mandatory")
    private Long aircraftId;

    private Integer displayOrder;
    private Boolean isActive;
    private Boolean isBookable;
    private Integer typicalSeatPitch;
    private Integer typicalSeatWidth;
    private String seatType;
}
