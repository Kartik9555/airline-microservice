package com.learning.pricing.service.service.impl;

import com.learning.common.payload.request.FareRequest;
import com.learning.common.payload.response.FareResponse;
import com.learning.pricing.service.mapper.FareMapper;
import com.learning.pricing.service.model.Fare;
import com.learning.pricing.service.repository.FareRepository;
import com.learning.pricing.service.service.FareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FareServiceImpl implements FareService {

    private final FareRepository fareRepository;

    @Override
    public FareResponse createFare(FareRequest request) throws Exception {
        if(fareRepository.existsByFlightIdAndCabinClassIdAndName(request.getFlightId(), request.getCabinClassId(), request.getName())) {
            throw new Exception("Fare with the same name already exists for this flight and cabin class");
        }
        final Fare fare = FareMapper.toFare(request);
        return FareMapper.toFare(fareRepository.save(fare));
    }

    @Override
    public FareResponse getFareById(Long fareId) throws Exception {
        final Fare fare = fareRepository.findById(fareId)
                .orElseThrow(() -> new Exception("Fare with id " + fareId + " does not exist"));
        return FareMapper.toFare(fare);
    }

    @Override
    public List<FareResponse> getFareByFlightIdAndCabinClassId(Long flightId, Long cabinClassId) throws Exception {
        return fareRepository.findByFlightIdAndCabinClassId(flightId, cabinClassId)
                .stream()
                .map(FareMapper::toFare)
                .toList();
    }

    @Override
    public FareResponse updateFare(Long id, FareRequest request) throws Exception {
        final Fare fare = fareRepository.findById(id)
                .orElseThrow(() -> new Exception("Fare with id " + id + " does not exist"));

        if(fareRepository.existsByFlightIdAndCabinClassIdAndNameAndIdNot(request.getFlightId(), request.getCabinClassId(), request.getName(), fare.getId())) {
            throw new Exception("Fare with the same name already exists for this flight and cabin class");
        }

        FareMapper.toFare(request,fare);
        return FareMapper.toFare(fareRepository.save(fare));
    }

    @Override
    public void deleteFareById(Long fareId) throws Exception {
        final Fare fare = fareRepository.findById(fareId)
                .orElseThrow(() -> new Exception("Fare with id " + fareId + " does not exist"));
        fareRepository.delete(fare);
    }

    @Override
    public List<FareResponse> getFares() {
        return fareRepository.findAll()
                .stream()
                .map(FareMapper::toFare)
                .toList();
    }

    @Override
    public Map<Long, FareResponse> getLowestFarePerFlight(List<Long> flightIds, Long cabinClassId) throws Exception {
        if(flightIds == null || flightIds.isEmpty()) return Collections.emptyMap();
        return fareRepository.findByFlightIdInAndCabinClassId(flightIds, cabinClassId)
                .stream()
                .collect(Collectors.groupingBy(Fare::getFlightId, Collectors.minBy(Comparator.comparing(Fare::getTotalPrice))))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> FareMapper.toFare(entry.getValue().get())));
    }

    @Override
    public Map<Long, FareResponse> getFareByIds(List<Long> fareIds) throws Exception {
        if(fareIds == null || fareIds.isEmpty()) return Collections.emptyMap();
        final List<Fare> fares = fareRepository.findAllById(fareIds);
        return fares.stream()
                .collect(Collectors.toMap(Fare::getId, FareMapper::toFare));
    }

    @Override
    public FareResponse getLowestFareByFlightIdAndCabinClassId(Long flightId, Long cabinClassId) throws Exception {
        List<Fare> fares = fareRepository.findByFlightIdAndCabinClassId(flightId, cabinClassId);
        Fare lowestFare = fares.stream()
                .min(Comparator.comparingDouble(Fare::getTotalPrice))
                .orElse(null);
        return FareMapper.toFare(lowestFare);
    }
}
