package com.learning.pricing.service.service;

import com.learning.common.payload.request.FareRuleRequest;
import com.learning.common.payload.response.FareRuleResponse;

import java.util.List;

public interface FareRuleService {
    FareRuleResponse getFareRuleById(Long fareRuleId) throws Exception;
    FareRuleResponse getFareRuleByFareId(Long fareId) throws Exception;
    List<FareRuleResponse> getFareRulesByAirlineId(Long airlineId) throws Exception;
    FareRuleResponse createFareRule(FareRuleRequest request) throws Exception;
    FareRuleResponse updateFareRule(Long fareRuleId, FareRuleRequest request) throws Exception;
    void deleteFareRule(Long fareRuleId) throws Exception;
}
