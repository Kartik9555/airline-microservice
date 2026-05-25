package com.learning.pricing.service.service.impl;

import com.learning.common.payload.request.FareRuleRequest;
import com.learning.common.payload.response.FareRuleResponse;
import com.learning.pricing.service.mapper.FareRuleMapper;
import com.learning.pricing.service.model.Fare;
import com.learning.pricing.service.model.FareRule;
import com.learning.pricing.service.repository.FareRepository;
import com.learning.pricing.service.repository.FareRuleRepository;
import com.learning.pricing.service.service.FareRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FareRuleServiceImpl implements FareRuleService {

    private final FareRuleRepository fareRulesRepository;
    private final FareRepository fareRepository;

    @Override
    public FareRuleResponse getFareRuleById(Long fareRuleId) throws Exception {
        final FareRule fareRule = fareRulesRepository.findById(fareRuleId)
                .orElseThrow(() -> new Exception("Fare rules not found with id: " + fareRuleId));
        return FareRuleMapper.toFareRule(fareRule);
    }

    @Override
    public FareRuleResponse getFareRuleByFareId(Long fareId) throws Exception {
        final FareRule fareRule = fareRulesRepository.findByFareId(fareId)
                .orElseThrow(() -> new Exception("Fare rules not found with id: " + fareId));
        return FareRuleMapper.toFareRule(fareRule);
    }

    @Override
    public List<FareRuleResponse> getFareRulesByAirlineId(Long airlineId) throws Exception {
        return fareRulesRepository.findByAirlineId(airlineId)
                .stream()
                .map(FareRuleMapper::toFareRule)
                .toList();
    }

    @Override
    public FareRuleResponse createFareRule(FareRuleRequest request) throws Exception {
        final Fare fare = fareRepository.findById(request.getFareId())
                .orElseThrow(() -> new Exception("Fare not found with id: " + request.getFareId()));

        if(fareRulesRepository.existsByFareId(fare.getId())) {
            throw new Exception("Fare Rules already exists with id: " + fare.getId());
        }

        final FareRule fareRule = FareRuleMapper.toFareRule(request, fare);
        return FareRuleMapper.toFareRule(fareRulesRepository.save(fareRule));
    }

    @Override
    public FareRuleResponse updateFareRule(Long fareRuleId, FareRuleRequest request) throws Exception {
        final FareRule fareRule = fareRulesRepository.findById(fareRuleId)
                .orElseThrow(() -> new Exception("Fare rules not found with id: " + fareRuleId));
        FareRuleMapper.toFareRule(fareRule, request);
        return FareRuleMapper.toFareRule(fareRulesRepository.save(fareRule));
    }

    @Override
    public void deleteFareRule(Long fareRuleId) throws Exception {
        final FareRule fareRule = fareRulesRepository.findById(fareRuleId)
                .orElseThrow(() -> new Exception("Fare rules not found with id: " + fareRuleId));
        fareRulesRepository.delete(fareRule);
    }
}
