package com.learning.ancillary.service.mapper;

import com.learning.ancillary.service.model.Ancillary;
import com.learning.ancillary.service.model.InsuranceCoverage;
import com.learning.common.payload.request.AncillaryRequest;
import com.learning.common.payload.response.AncillaryResponse;

import java.util.List;

public class AncillaryMapper {

    public static AncillaryResponse toAncillary(Ancillary ancillary, List<InsuranceCoverage> coverages) {
        if (ancillary == null) return null;
        return AncillaryResponse.builder()
                .id(ancillary.getId())
                .type(ancillary.getType())
                .subType(ancillary.getSubType())
                .rfisc(ancillary.getRfisc())
                .name(ancillary.getName())
                .description(ancillary.getDescription())
                .metadata(ancillary.getMetadata())
                .coverages(coverages == null ? null : coverages.stream().map(InsuranceCoverageMapper::toInsuranceCoverage).toList())
                .displayOrder(ancillary.getDisplayOrder())
                .airlineId(ancillary.getAirlineId())
                .build();
    }

    public static Ancillary toAncillary(AncillaryRequest request) {
        if (request == null) return null;
        return Ancillary.builder()
                .type(request.getType())
                .subType(request.getSubType())
                .rfisc(request.getRfisc())
                .name(request.getName())
                .description(request.getDescription())
                .metadata(request.getMetadata())
                .displayOrder(request.getDisplayOrder())
                .build();
    }

    public static void toAncillary(AncillaryRequest request, Ancillary ancillary) {
        if (request == null || ancillary == null) return;
        ancillary.setType(request.getType());
        ancillary.setSubType(request.getSubType());
        ancillary.setRfisc(request.getRfisc());
        ancillary.setName(request.getName());
        ancillary.setDescription(request.getDescription());
        ancillary.setMetadata(request.getMetadata());
        ancillary.setDisplayOrder(request.getDisplayOrder());
    }
}
