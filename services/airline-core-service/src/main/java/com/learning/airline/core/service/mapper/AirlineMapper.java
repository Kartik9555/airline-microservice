package com.learning.airline.core.service.mapper;

import com.learning.airline.core.service.model.Airline;
import com.learning.common.embeddable.Support;
import com.learning.common.payload.request.AirlineRequest;
import com.learning.common.payload.response.AirlineResponse;

import java.time.Instant;

public class AirlineMapper {
    public static Airline toAirline(AirlineRequest request, Long ownerId) {
        if(request == null) return null;

        final Instant now = Instant.now();
        final Airline airline = Airline.builder()
                .iataCode(request.getIataCode())
                .icaoCode(request.getIcaoCode())
                .ownerId(ownerId)
                .name(request.getName())
                .alias(request.getAlias())
                .logoUrl(request.getLogoUrl())
                .website(request.getWebsite())
                .status(request.getStatus())
                .alliance(request.getAlliance())
                .headquarterCityId(request.getHeadquarterCityId())
                .build();

        if(request.getSupportEmail() != null || request.getSupportPhone() != null || request.getSupportHours() != null) {
            airline.setSupport(Support.builder()
                    .email(request.getSupportEmail())
                    .phone(request.getSupportPhone())
                    .hours(request.getSupportHours())
                    .build());
        }
        return airline;
    }

    public static AirlineResponse toAirline(Airline airline) {
        if(airline == null) return null;
        return AirlineResponse.builder()
                .id(airline.getId())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .name(airline.getName())
                .alias(airline.getAlias())
                .logoUrl(airline.getLogoUrl())
                .website(airline.getWebsite())
                .status(airline.getStatus())
                .alliance(airline.getAlliance())
                .createdAt(airline.getCreatedAt())
                .updatedAt(airline.getUpdatedAt())
                .ownerId(airline.getOwnerId())
                .updatedById(airline.getUpdatedById())
                .support(airline.getSupport())
                .build();
    }

    public static void toAirline(Airline airline, AirlineRequest request) {
        if(airline == null || request == null) return;
        airline.setIataCode(request.getIataCode());
        airline.setIcaoCode(request.getIcaoCode());
        airline.setName(request.getName());
        airline.setAlias(request.getAlias());
        airline.setLogoUrl(request.getLogoUrl());
        airline.setWebsite(request.getWebsite());
        airline.setStatus(request.getStatus());
        airline.setAlliance(request.getAlliance());
        airline.setHeadquarterCityId(request.getHeadquarterCityId());
        if(airline.getSupport() == null) {
            airline.setSupport(Support.builder().build());
        }
        airline.getSupport().setEmail(request.getSupportEmail());
        airline.getSupport().setPhone(request.getSupportPhone());
        airline.getSupport().setHours(request.getSupportHours());
    }
}
