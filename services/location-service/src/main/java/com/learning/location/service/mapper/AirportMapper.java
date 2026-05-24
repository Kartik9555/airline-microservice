package com.learning.location.service.mapper;

import com.learning.common.payload.request.AirportRequest;
import com.learning.common.payload.response.AirportResponse;
import com.learning.common.payload.response.CityResponse;
import com.learning.location.service.model.Airport;

import java.time.ZoneId;

public class AirportMapper {

    public static AirportResponse toAirport(Airport airport) {
        if(airport == null) {
            return null;
        }
        final CityResponse city = CityMapper.toCity(airport.getCity());
        return AirportResponse.builder()
                .id(airport.getId())
                .name(airport.getName())
                .iataCode(airport.getIataCode())
                .cityResponse(city)
                .detailedName(airport.getDetailedName())
                .timeZone(ZoneId.of(city.getTimeZoneOffset()))
                .address(airport.getAddress())
                .geoCode(airport.getGeoCode())
                .build();
    }

    public static Airport toAirport(AirportRequest request) {
        if(request == null) {
            return null;
        }
        return Airport.builder()
                .name(request.getName())
                .iataCode(request.getIataCode())
                .name(request.getName())
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .build();
    }

    public static void toAirport(Airport airport, AirportRequest request) {
        if(airport == null || request == null) return;

        if(request.getIataCode() != null) {
            airport.setIataCode(request.getIataCode().trim());
        }

        if(request.getName() != null) {
            airport.setName(request.getName().trim());
        }

        if(request.getAddress() != null) {
            airport.setAddress(request.getAddress());
        }

        if(request.getGeoCode() != null) {
            airport.setGeoCode(request.getGeoCode());
        }
    }
}
