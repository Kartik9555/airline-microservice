package com.learning.location.service.mapper;

import com.learning.common.payload.request.CityRequest;
import com.learning.common.payload.response.CityResponse;
import com.learning.location.service.model.City;

public class CityMapper {
    public static CityResponse toCity(City city) {
        if(city == null) {
            return null;
        }
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .cityCode(city.getCityCode())
                .countryCode(city.getCountryCode())
                .countryName(city.getCountryName())
                .regionCode(city.getRegionCode())
                .timeZoneOffset(city.getTimeZoneId())
                .build();
    }

    public static City toCity(CityRequest request) {
        if(request == null) {
            return null;
        }
        return City.builder()
                .name(request.getName())
                .cityCode(request.getCityCode())
                .countryCode(request.getCountryCode())
                .countryName(request.getCountryName())
                .regionCode(request.getRegionCode())
                .timeZoneId(request.getTimeZoneOffset())
                .build();
    }

    public static void toCity(City city, CityRequest request) {
        if(city == null || request == null) return;

        if(request.getName() != null) {
            city.setName(request.getName().trim());
        }
        if(request.getCityCode() != null) {
            city.setCityCode(request.getCityCode().toUpperCase().trim());
        }
        if(request.getCountryCode() != null) {
            city.setCountryCode(request.getCountryCode().toUpperCase().trim());
        }
        if(request.getRegionCode() != null) {
            city.setRegionCode(request.getRegionCode().toUpperCase().trim());
        }
        if(request.getCountryName() != null) {
            city.setCountryName(request.getCountryName().trim());
        }
    }
}
