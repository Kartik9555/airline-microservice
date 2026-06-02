package com.learning.pricing.service.service.impl;

import com.learning.common.payload.request.BaggagePolicyRequest;
import com.learning.common.payload.response.BaggagePolicyResponse;
import com.learning.pricing.service.mapper.BaggagePolicyMapper;
import com.learning.pricing.service.model.BaggagePolicy;
import com.learning.pricing.service.model.Fare;
import com.learning.pricing.service.repository.BaggagePolicyRepository;
import com.learning.pricing.service.repository.FareRepository;
import com.learning.pricing.service.service.BaggagePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaggagePolicyServiceImpl implements BaggagePolicyService {

    private final BaggagePolicyRepository baggagePolicyRepository;
    private final FareRepository fareRepository;

    @Override
    @Transactional(readOnly = true)
    public BaggagePolicyResponse getBaggagePolicyById(Long id) throws Exception {
        final BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id)
                .orElseThrow(() -> new Exception("Baggage Policy not found with id: " + id));
        return BaggagePolicyMapper.toBaggagePolicy(baggagePolicy);
    }

    @Override
    @Transactional(readOnly = true)
    public BaggagePolicyResponse getBaggagePolicyByFareId(Long fareId) throws Exception {
        final BaggagePolicy baggagePolicy = baggagePolicyRepository.findByFareId(fareId)
                .orElseThrow(() -> new Exception("Baggage Policy not found with fare id: " + fareId));
        return BaggagePolicyMapper.toBaggagePolicy(baggagePolicy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BaggagePolicyResponse> getBaggagePoliciesByAirlineId(Long airlineId) throws Exception {
        return baggagePolicyRepository.findByAirlineId(airlineId)
                .stream()
                .map(BaggagePolicyMapper::toBaggagePolicy)
                .toList();
    }

    @Override
    @Transactional
    public BaggagePolicyResponse createBaggagePolicy(BaggagePolicyRequest request) throws Exception {
        final Fare fare = fareRepository.findById(request.getFareId())
                .orElseThrow(() -> new Exception("Fare id not found"));

        if(baggagePolicyRepository.existsByFareId(request.getFareId())) {
            throw new Exception("Baggage Policy already exists");
        }

        final BaggagePolicy baggagePolicy = BaggagePolicyMapper.toBaggagePolicy(request, fare);
        return BaggagePolicyMapper.toBaggagePolicy(baggagePolicyRepository.save(baggagePolicy));
    }

    @Override
    @Transactional
    public BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) throws Exception {
        final BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id)
                .orElseThrow(() -> new Exception("Baggage Policy not found with id: " + id));
        BaggagePolicyMapper.toBaggagePolicy(request, baggagePolicy);
        return BaggagePolicyMapper.toBaggagePolicy(baggagePolicyRepository.save(baggagePolicy));
    }

    @Override
    @Transactional
    public void deleteBaggagePolicy(Long id) throws Exception {
        final BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id)
                .orElseThrow(() -> new Exception("Baggage Policy not found with id: " + id));
        baggagePolicyRepository.delete(baggagePolicy);
    }
}
