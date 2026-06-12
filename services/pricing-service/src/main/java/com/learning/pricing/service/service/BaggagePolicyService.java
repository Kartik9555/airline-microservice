package com.learning.pricing.service.service;

import com.learning.common.payload.request.BaggagePolicyRequest;
import com.learning.common.payload.response.BaggagePolicyResponse;

import java.util.List;

public interface BaggagePolicyService {
    BaggagePolicyResponse getBaggagePolicyById(Long id) throws Exception;
    BaggagePolicyResponse getBaggagePolicyByFareId(Long fareId) throws Exception;
    List<BaggagePolicyResponse> getBaggagePoliciesByAirlineId(Long airlineId) throws Exception;
    BaggagePolicyResponse createBaggagePolicy(Long userId, BaggagePolicyRequest request) throws Exception;
    BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) throws Exception;
    void deleteBaggagePolicy(Long id) throws Exception;
}
