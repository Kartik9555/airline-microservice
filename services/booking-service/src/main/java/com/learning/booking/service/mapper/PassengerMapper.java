package com.learning.booking.service.mapper;

import com.learning.booking.service.model.Passenger;
import com.learning.common.payload.request.PassengerRequest;
import com.learning.common.payload.response.PassengerResponse;

public class PassengerMapper {

    public static Passenger toPassenger(PassengerRequest request) {
        if(request == null) return null;
        return Passenger.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .passportNumber(request.getPassportNumber())
                .nationality(request.getNationality())
                .frequentFlyerNumber(request.getFrequentFlyerNumber())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .requiresWheelchairAssistance(request.getRequiresWheelchairAssistance() != null ? request.getRequiresWheelchairAssistance() : false)
                .dietaryPreferences(request.getDietaryPreferences())
                .medicalConditions(request.getMedicalConditions())
                .build();
    }

    public static PassengerResponse toPassenger(Passenger passenger) {
        if(passenger == null) return null;
        return PassengerResponse.builder()
                .id(passenger.getId())
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .email(passenger.getEmail())
                .phone(passenger.getPhone())
                .dateOfBirth(passenger.getDateOfBirth())
                .gender(passenger.getGender())
                .passportNumber(passenger.getPassportNumber())
                .nationality(passenger.getNationality())
                .frequentFlyerNumber(passenger.getFrequentFlyerNumber())
                .primaryUserId(passenger.getPrimaryUserId())
                .requiresWheelchairAssistance(passenger.getRequiresWheelchairAssistance())
                .dietaryPreferences(passenger.getDietaryPreferences())
                .medicalConditions(passenger.getMedicalConditions())
                .isActive(passenger.getIsActive())
                .age(passenger.getAge())
                .isAdult(passenger.isAdult())
                .fullName(passenger.getFullName())
                .createdAt(passenger.getCreatedAt())
                .updatedAt(passenger.getUpdatedAt())
                .build();
    }
}
