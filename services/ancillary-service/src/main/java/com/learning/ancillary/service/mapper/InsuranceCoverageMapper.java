package com.learning.ancillary.service.mapper;

import com.learning.ancillary.service.model.Ancillary;
import com.learning.ancillary.service.model.InsuranceCoverage;
import com.learning.common.payload.request.InsuranceCoverageRequest;
import com.learning.common.payload.response.InsuranceCoverageResponse;

public class InsuranceCoverageMapper {
    public static InsuranceCoverage toInsuranceCoverage(InsuranceCoverageRequest request, Ancillary ancillary) {
        if(request == null) return null;

        return InsuranceCoverage.builder()
                .coverageType(request.getCoverageType())
                .name(request.getName())
                .description(request.getDescription())
                .coverageAmount(request.getCoverageAmount())
                .claimCondition(request.getClaimCondition())
                .displayOrder(request.getDisplayOrder())
                .active(request.getActive() != null && request.getActive())
                .isFlat(request.getIsFlat() != null ? request.getIsFlat() : false)
                .ancillary(ancillary)
                .emergencyContact(request.getEmergencyContact())
                .build();

    }

    public static InsuranceCoverageResponse toInsuranceCoverage(InsuranceCoverage insuranceCoverage) {
        if(insuranceCoverage == null) return null;
        return InsuranceCoverageResponse.builder()
                .id(insuranceCoverage.getId())
                .ancillaryId(insuranceCoverage.getAncillary() != null ? insuranceCoverage.getAncillary().getId() : null)
                .ancillaryName(insuranceCoverage.getAncillary() != null ? insuranceCoverage.getAncillary().getName() : null)
                .coverageType(insuranceCoverage.getCoverageType())
                .name(insuranceCoverage.getName())
                .description(insuranceCoverage.getDescription())
                .coverageAmount(insuranceCoverage.getCoverageAmount())
                .isFlat(insuranceCoverage.getIsFlat())
                .claimCondition(insuranceCoverage.getClaimCondition())
                .emergencyContact(insuranceCoverage.getEmergencyContact())
                .displayOrder(insuranceCoverage.getDisplayOrder())
                .active(insuranceCoverage.getActive())
                .build();
    }

    public static void toInsuranceCoverage(InsuranceCoverageRequest request, InsuranceCoverage insuranceCoverage, Ancillary ancillary) {
        if(request == null || insuranceCoverage == null) return;
        if(ancillary != null)  insuranceCoverage.setAncillary(ancillary);
        if(request.getCoverageType() != null) insuranceCoverage.setCoverageType(request.getCoverageType());
        if(request.getName() != null) insuranceCoverage.setName(request.getName());
        if(request.getDescription() != null) insuranceCoverage.setDescription(request.getDescription());
        if(request.getCoverageAmount() != null) insuranceCoverage.setCoverageAmount(request.getCoverageAmount());
        if(request.getClaimCondition() != null) insuranceCoverage.setClaimCondition(request.getClaimCondition());
        if(request.getEmergencyContact() != null) insuranceCoverage.setEmergencyContact(request.getEmergencyContact());
        if(request.getDisplayOrder() != null) insuranceCoverage.setDisplayOrder(request.getDisplayOrder());
        if(request.getActive() != null) insuranceCoverage.setActive(request.getActive());
        if(request.getIsFlat() != null) insuranceCoverage.setIsFlat(request.getIsFlat());
    }
}
