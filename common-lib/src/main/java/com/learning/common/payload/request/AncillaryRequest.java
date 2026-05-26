package com.learning.common.payload.request;

import com.learning.common.domain.AncillaryMetadata;
import com.learning.common.enums.AncillaryType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AncillaryRequest {

    @NotNull(message = "Ancillary Type is required")
    private AncillaryType type;

    @Size(max = 100, message = "Subtype must be at most 100 characters")
    private String subType;

    @Size(max = 10, message = "RFISC must be at most 10 characters")
    private String rfisc;

    @Size(max = 200, message = "Name must be at most 200 characters")
    private String name;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    @Size(max = 500, message = "Icon URL must be at most 500 characters")
    private String iconUrl;

    private AncillaryMetadata metadata;

    private Integer displayOrder;
}
