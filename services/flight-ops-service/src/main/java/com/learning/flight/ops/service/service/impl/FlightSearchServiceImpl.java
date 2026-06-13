package com.learning.flight.ops.service.service.impl;

import com.learning.common.enums.CabinClassType;
import com.learning.common.payload.request.FlightSearchRequest;
import com.learning.common.payload.response.AircraftResponse;
import com.learning.common.payload.response.AirlineResponse;
import com.learning.common.payload.response.AirportResponse;
import com.learning.common.payload.response.CabinClassResponse;
import com.learning.common.payload.response.FareResponse;
import com.learning.common.payload.response.FlightInstanceResponse;
import com.learning.flight.ops.service.mapper.FlightInstanceMapper;
import com.learning.flight.ops.service.model.FlightInstance;
import com.learning.flight.ops.service.repository.FlightInstanceRepository;
import com.learning.flight.ops.service.service.FlightSearchService;
import com.learning.flight.ops.service.service.outbound.AircraftOutboundService;
import com.learning.flight.ops.service.service.outbound.AirlineOutboundService;
import com.learning.flight.ops.service.service.outbound.AirportOutboundService;
import com.learning.flight.ops.service.service.outbound.PriceOutboundService;
import com.learning.flight.ops.service.service.outbound.SeatOutboundService;
import com.learning.flight.ops.service.service.specificaton.FlightInstanceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final PriceOutboundService pricingService;
    private final SeatOutboundService seatService;
    private final AirlineOutboundService airlineService;
    private final AircraftOutboundService aircraftService;
    private final AirportOutboundService airportService;

    @Override
    @Transactional(readOnly = true)
    public Page<FlightInstanceResponse> searchFlights(FlightSearchRequest request, Pageable pageable) {
        final Pageable sortedPageable = applySort(pageable, request.getSortBy(), request.getSortOrder());
        final Specification<FlightInstance> specification = FlightInstanceSpecification.buildSpecification(request);
        final Page<FlightInstance> page = flightInstanceRepository.findAll(specification, sortedPageable);
        if(page.isEmpty()) {
            return Page.empty(sortedPageable);
        }
        List<FlightInstance> flightInstances = new ArrayList<>(page.getContent());
        Map<Long, FareResponse> fares = Collections.emptyMap();
        if(request.getCabinClass() != null) {
            final boolean hasPriceFilter = request.getMaxPrice() != null && request.getMinPrice() != null;
            Map<Long, FareResponse> mergedFareMap = new HashMap<>();
            List<FlightInstance> filteredFlightInstances = new ArrayList<>();
            for (FlightInstance flightInstance : flightInstances) {
                Long cabinClassId = resolveCabinClassId(request.getCabinClass(), flightInstance.getFlight().getAircraftId());
                if(cabinClassId == null) {
                    continue;
                }
                FareResponse fare = pricingService.getLowestFareForFlightAndCabinClass(flightInstance.getFlight().getId(), cabinClassId);
                if(fare == null) {
                    continue;
                }
                if(hasPriceFilter) {
                    Double price = fare.getTotalPrice();
                    if(price == null) continue;
                    if(price < request.getMinPrice()) continue;
                    if(price > request.getMaxPrice()) continue;
                }
                mergedFareMap.put(flightInstance.getFlight().getId(), fare);
                filteredFlightInstances.add(flightInstance);
            }
            fares = mergedFareMap;
            flightInstances = filteredFlightInstances;
            if(flightInstances.isEmpty()) {
                return Page.empty(sortedPageable);
            }
        }
        List<FlightInstanceResponse> flightInstancesResponse = enrichWithExternalDate(flightInstances, fares);
        return new PageImpl<>(flightInstancesResponse, pageable, page.getTotalElements());
    }

    private List<FlightInstanceResponse> enrichWithExternalDate(List<FlightInstance> flightInstances, Map<Long, FareResponse> fares) {
        final Map<Long, AirlineResponse> airlines = new HashMap<>();
        final Map<Long, AirportResponse> airports = new HashMap<>();
        final Map<Long, AircraftResponse> aircrafts = new HashMap<>();
        final List<FlightInstanceResponse> results = new ArrayList<>(flightInstances.size());
        for (FlightInstance flightInstance : flightInstances) {
            final AirlineResponse airline = airlines.computeIfAbsent(flightInstance.getFlight().getAirlineId(), airlineService::getAirlineById);
            final AirportResponse arrivalAirport = airports.computeIfAbsent(flightInstance.getFlight().getArrivalAirportId(), airportService::getAirportById);
            final AirportResponse departureAirport = airports.computeIfAbsent(flightInstance.getFlight().getDepartureAirportId(), airportService::getAirportById);
            final AircraftResponse aircraft = aircrafts.computeIfAbsent(flightInstance.getFlight().getAircraftId(), aircraftService::getAircraftById);
            final FlightInstanceResponse response = FlightInstanceMapper.toFlightInstance(flightInstance, aircraft, airline, departureAirport, arrivalAirport);
            response.setFare(fares.get(flightInstance.getFlight().getId()));
            results.add(response);
        }

        return results;
    }

    private Long resolveCabinClassId(CabinClassType cabinClass, Long aircraftId) {
        final CabinClassResponse cabinClassResponse = seatService.getCabinClassByAircraftIdAndName(aircraftId, cabinClass);
        return cabinClassResponse.getId();
    }

    private Pageable applySort(Pageable pageable, String sortBy, String sortOrder) {
        final Sort.Direction sortDirection = "desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
        final Sort sort = sortBy == null || sortBy.isBlank()
                ? Sort.by(sortDirection, "departureDateTime")
                : switch (sortBy.toLowerCase()) {
            case "arrival" -> Sort.by(sortDirection, "arrivalDateTime");
            case "duration" -> JpaSort.unsafe(sortDirection, "TIMESTAMPDIFF(MINUTE, departureDateTime, arrivalDateTime)");
            default -> Sort.by(sortDirection, "departureDateTime");
        };
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }
}
