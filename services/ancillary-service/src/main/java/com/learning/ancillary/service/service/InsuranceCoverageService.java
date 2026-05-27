package com.learning.ancillary.service.service;

import com.learning.common.payload.request.InsuranceCoverageRequest;
import com.learning.common.payload.response.InsuranceCoverageResponse;

import java.util.List;

public interface InsuranceCoverageService {
    InsuranceCoverageResponse getInsuranceCoverageById(Long id) throws Exception;
    List<InsuranceCoverageResponse> getInsuranceCoveragesByAncillaryId(Long ancillaryId) throws Exception;
    List<InsuranceCoverageResponse> getActiveInsuranceCoveragesByAncillaryId(Long ancillaryId) throws Exception;
    List<InsuranceCoverageResponse> getAllCoverages() throws Exception;
    InsuranceCoverageResponse createInsuranceCoverage(InsuranceCoverageRequest request) throws Exception;
    InsuranceCoverageResponse updateInsuranceCoverage(Long id, InsuranceCoverageRequest request) throws Exception;
    void deleteInsuranceCoverageById(Long id) throws Exception;
}
