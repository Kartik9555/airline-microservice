package com.learning.ancillary.service.service.impl;

import com.learning.ancillary.service.mapper.AncillaryMapper;
import com.learning.ancillary.service.model.Ancillary;
import com.learning.ancillary.service.model.InsuranceCoverage;
import com.learning.ancillary.service.repository.AncillaryRepository;
import com.learning.ancillary.service.repository.InsuranceCoverageRepository;
import com.learning.ancillary.service.service.AncillaryService;
import com.learning.common.payload.request.AncillaryRequest;
import com.learning.common.payload.response.AncillaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AncillaryServiceImpl implements AncillaryService {
    private final AncillaryRepository ancillaryRepository;
    private final InsuranceCoverageRepository insuranceCoverageRepository;

    @Override
    public AncillaryResponse getById(Long id) throws Exception {
        final Ancillary ancillary = ancillaryRepository.findById(id)
                .orElseThrow(() -> new Exception("Ancillary not found"));

        final List<InsuranceCoverage> coverages = insuranceCoverageRepository.findByAncillaryId(id);
        return AncillaryMapper.toAncillary(ancillary, coverages);
    }

    @Override
    public List<AncillaryResponse> getByAirlineId(Long airlineId) throws Exception {
        return ancillaryRepository.findByAirlineId(airlineId)
                .stream()
                .map(ancillary ->  {
                    final List<InsuranceCoverage> coverages = insuranceCoverageRepository.findByAncillaryId(ancillary.getId());
                    return AncillaryMapper.toAncillary(ancillary, coverages);
                })
                .toList();
    }

    @Override
    public AncillaryResponse createAncillary(Long airlineId, AncillaryRequest request) {
        final Ancillary ancillary = AncillaryMapper.toAncillary(request);
        ancillary.setAirlineId(airlineId);
        return AncillaryMapper.toAncillary(ancillaryRepository.save(ancillary), null);
    }

    @Override
    public AncillaryResponse updateAncillary(Long id, AncillaryRequest request) throws Exception {
        final Ancillary ancillary = ancillaryRepository.findById(id)
                .orElseThrow(() -> new Exception("Ancillary not found"));

        AncillaryMapper.toAncillary(request, ancillary);
        final Ancillary saved = ancillaryRepository.save(ancillary);
        final List<InsuranceCoverage> coverages = insuranceCoverageRepository.findByAncillaryId(saved.getId());
        return AncillaryMapper.toAncillary(saved, coverages);
    }

    @Override
    public void deleteAncillary(Long id) throws Exception {
        final Ancillary ancillary = ancillaryRepository.findById(id)
                .orElseThrow(() -> new Exception("Ancillary not found"));
        ancillaryRepository.delete(ancillary);
    }
}
