package com.learning.location.service.service.impl;

import com.learning.common.payload.request.AirportRequest;
import com.learning.common.payload.response.AirportResponse;
import com.learning.location.service.mapper.AirportMapper;
import com.learning.location.service.model.Airport;
import com.learning.location.service.model.City;
import com.learning.location.service.repository.AirportRepository;
import com.learning.location.service.repository.CityRepository;
import com.learning.location.service.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest airportRequest) throws Exception {
        if(airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()) {
            throw new Exception("Airport with IATA code " + airportRequest.getIataCode() + " already exists.");
        }
        final City city = cityRepository.findById(airportRequest.getCityId()).orElseThrow(
                () -> new Exception("City not found with id: " + airportRequest.getCityId())
        );
        final Airport airport = AirportMapper.toAirport(airportRequest);
        airport.setCity(city);
        airport.setTimeZoneId(city.getTimeZoneId());
        final Airport saved = airportRepository.save(airport);
        return AirportMapper.toAirport(saved);
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        final Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport not found with id: " + id));
        return AirportMapper.toAirport(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream().map(AirportMapper::toAirport).toList();
    }

    @Override
    public List<AirportResponse> getAirportsByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId).stream().map(AirportMapper::toAirport).toList();
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest airportRequest) throws Exception {
        final Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport not found with id: " + id));

        if(airportRequest.getIataCode() != null &&
                !existingAirport.getIataCode().equals(airportRequest.getIataCode()) &&
        airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()) {
            throw new Exception("Airport with IATA code " + airportRequest.getIataCode() + " already exists.");
        }

        AirportMapper.toAirport(existingAirport, airportRequest);
        final Airport saved = airportRepository.save(existingAirport);
        return AirportMapper.toAirport(saved);
    }

    @Override
    public void deleteAirport(Long id) throws Exception {
        airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport not found with id: " + id));
        airportRepository.deleteById(id);
    }
}
