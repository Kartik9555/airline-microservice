package com.learning.ancillary.service.service.impl;

import com.learning.ancillary.service.mapper.InsuranceCoverageMapper;
import com.learning.ancillary.service.model.Ancillary;
import com.learning.ancillary.service.model.InsuranceCoverage;
import com.learning.ancillary.service.model.InsuranceCoverageRepository;
import com.learning.ancillary.service.repository.AncillaryRepository;
import com.learning.ancillary.service.service.InsuranceCoverageService;
import com.learning.common.payload.request.InsuranceCoverageRequest;
import com.learning.common.payload.response.InsuranceCoverageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceCoverageServiceImpl implements InsuranceCoverageService {
    private final InsuranceCoverageRepository insuranceCoverageRepository;
    private final AncillaryRepository ancillaryRepository;

    @Override
    public InsuranceCoverageResponse getInsuranceCoverageById(Long id) throws Exception {
        final InsuranceCoverage insuranceCoverage = insuranceCoverageRepository.findById(id)
                .orElseThrow(() -> new Exception("Insurance coverage not found with id " + id));
        return InsuranceCoverageMapper.toInsuranceCoverage(insuranceCoverage);
    }

    @Override
    public List<InsuranceCoverageResponse> getInsuranceCoveragesByAncillaryId(Long ancillaryId) throws Exception {
        return insuranceCoverageRepository.findByAncillaryId(ancillaryId)
                .stream()
                .map(InsuranceCoverageMapper::toInsuranceCoverage)
                .toList();
    }

    @Override
    public List<InsuranceCoverageResponse> getActiveInsuranceCoveragesByAncillaryId(Long ancillaryId) throws Exception {
        return insuranceCoverageRepository.findByAncillaryIdAndActiveTrue(ancillaryId)
                .stream()
                .map(InsuranceCoverageMapper::toInsuranceCoverage)
                .toList();
    }

    @Override
    public List<InsuranceCoverageResponse> getAllCoverages() throws Exception {
        return insuranceCoverageRepository.findAll()
                .stream()
                .map(InsuranceCoverageMapper::toInsuranceCoverage)
                .toList();
    }

    @Override
    public InsuranceCoverageResponse createInsuranceCoverage(InsuranceCoverageRequest request) throws Exception {
        final Ancillary ancillary = ancillaryRepository.findById(request.getAncillaryId()).
                orElseThrow(() -> new Exception("Ancillary not found with id " + request.getAncillaryId()));
        final InsuranceCoverage insuranceCoverage = InsuranceCoverageMapper.toInsuranceCoverage(request, ancillary);
        return InsuranceCoverageMapper.toInsuranceCoverage(insuranceCoverageRepository.save(insuranceCoverage));
    }

    @Override
    public InsuranceCoverageResponse updateInsuranceCoverage(Long id, InsuranceCoverageRequest request) throws Exception {
        final InsuranceCoverage insuranceCoverage = insuranceCoverageRepository.findById(id)
                .orElseThrow(() -> new Exception("Insurance coverage not found with id " + id));

        Ancillary ancillary = null;
        if(request.getAncillaryId() != null) {
            ancillary = ancillaryRepository.findById(request.getAncillaryId())
                    .orElseThrow(() -> new Exception("Ancillary not found with id " + request.getAncillaryId()));
        }
        InsuranceCoverageMapper.toInsuranceCoverage(request, insuranceCoverage, ancillary);
        return InsuranceCoverageMapper.toInsuranceCoverage(insuranceCoverage);
    }

    @Override
    public void deleteInsuranceCoverageById(Long id) throws Exception {
        final InsuranceCoverage insuranceCoverage = insuranceCoverageRepository.findById(id)
                .orElseThrow(() -> new Exception("Insurance coverage not found with id " + id));
        insuranceCoverageRepository.delete(insuranceCoverage);
    }
}
