package com.learning.ancillary.service.service.impl;

import com.learning.ancillary.service.mapper.FlightCabinAncillaryMapper;
import com.learning.ancillary.service.model.Ancillary;
import com.learning.ancillary.service.model.FlightCabinAncillary;
import com.learning.ancillary.service.model.InsuranceCoverage;
import com.learning.ancillary.service.repository.AncillaryRepository;
import com.learning.ancillary.service.repository.FlightCabinAncillaryRepository;
import com.learning.ancillary.service.repository.InsuranceCoverageRepository;
import com.learning.ancillary.service.service.FlightCabinAncillaryService;
import com.learning.common.enums.AncillaryType;
import com.learning.common.payload.request.FlightCabinAncillaryRequest;
import com.learning.common.payload.response.FlightCabinAncillaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightCabinAncillaryServiceImpl implements FlightCabinAncillaryService {

    private final FlightCabinAncillaryRepository flightCabinAncillaryRepository;
    private final AncillaryRepository ancillaryRepository;
    private final InsuranceCoverageRepository insuranceCoverageRepository;

    @Override
    public FlightCabinAncillaryResponse getFlightCabinAncillaryById(Long id) throws Exception {
        final FlightCabinAncillary flightCabinAncillary = flightCabinAncillaryRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight Cabin Ancillary not found"));
        return getFlightCabinAncillaryResponse(flightCabinAncillary);
    }

    @Override
    public List<FlightCabinAncillaryResponse> getByFlightAndCabinClass(Long flight, Long CabinClassId) {
        return flightCabinAncillaryRepository.findByFlightIdAndCabinClassId(flight, CabinClassId)
                .stream()
                .map(this::getFlightCabinAncillaryResponse)
                .toList();

    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByIds(List<Long> ids) {
        return flightCabinAncillaryRepository.findAllById(ids)
                .stream()
                .map(this::getFlightCabinAncillaryResponse)
                .toList();
    }

    @Override
    public List<FlightCabinAncillaryResponse> getAllByFlightIdAndCabinClassIdAndAncillaryType(Long flightId, Long cabinClassId, AncillaryType ancillaryType) {
        return flightCabinAncillaryRepository.findAllByFlightIdAndCabinClassIdAndAncillaryType(flightId, cabinClassId, ancillaryType)
                .stream()
                .map(this::getFlightCabinAncillaryResponse)
                .toList();
    }

    @Override
    public FlightCabinAncillaryResponse getByFlightIdAndCabinClassIdAndAncillaryType(Long flightId, Long cabinClassId, AncillaryType ancillaryType) throws Exception {
        final FlightCabinAncillary flightCabinAncillary = flightCabinAncillaryRepository.findByFlightIdAndCabinClassIdAndAncillaryType(flightId, cabinClassId, ancillaryType)
                .orElseThrow(() -> new Exception("Flight Cabin Ancillary not found"));
        return getFlightCabinAncillaryResponse(flightCabinAncillary);
    }

    @Override
    public FlightCabinAncillaryResponse createFlightCabinAncillary(FlightCabinAncillaryRequest request) throws Exception {
        final Ancillary ancillary = ancillaryRepository.findById(request.getAncillaryId())
                .orElseThrow(() -> new Exception("Ancillary not found"));
        final FlightCabinAncillary saved = flightCabinAncillaryRepository.save(FlightCabinAncillaryMapper.toFlightCabinAncillary(request, ancillary));
        return getFlightCabinAncillaryResponse(saved);
    }

    @Override
    public FlightCabinAncillaryResponse updateFlightCabinAncillary(Long id, FlightCabinAncillaryRequest request) throws Exception {
        final FlightCabinAncillary flightCabinAncillary = flightCabinAncillaryRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight Cabin Ancillary not found"));
        FlightCabinAncillaryMapper.toFlightCabinAncillary(request, flightCabinAncillary);
        final FlightCabinAncillary saved = flightCabinAncillaryRepository.save(flightCabinAncillary);
        return getFlightCabinAncillaryResponse(saved);
    }

    @Override
    public void deleteFlightCabinAncillary(Long id) throws Exception {
        final FlightCabinAncillary flightCabinAncillary = flightCabinAncillaryRepository.findById(id)
                .orElseThrow(() -> new Exception("Flight Cabin Ancillary not found"));
        flightCabinAncillaryRepository.delete(flightCabinAncillary);
    }

    @Override
    public Double calculateAncillaryPrice(List<Long> ids) {
        final List<FlightCabinAncillary> flightCabinAncillaries = flightCabinAncillaryRepository.findAllById(ids);
        Double price = 0.0;
        for (FlightCabinAncillary ancillary : flightCabinAncillaries) {
            price += ancillary.getPrice();
        }
        return price;
    }

    private FlightCabinAncillaryResponse getFlightCabinAncillaryResponse(FlightCabinAncillary flightCabinAncillary) {
        final List<InsuranceCoverage> coverages = insuranceCoverageRepository.findByAncillaryId(flightCabinAncillary.getAncillary().getId());
        return FlightCabinAncillaryMapper.toFlightCabinAncillary(flightCabinAncillary, coverages);
    }
}
