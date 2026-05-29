package com.learning.common.payload.response;

import com.learning.common.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String passportNumber;
    private String nationality;
    private String frequentFlyerNumber;
    private Long primaryUserId;
    private String primaryUserName;
    private Boolean requiresWheelchairAssistance;
    private String dietaryPreferences;
    private String medicalConditions;
    private Boolean isActive;
    private Integer age;
    private Boolean isAdult;
    private String fullName;
    private Instant createdAt;
    private Instant updatedAt;
}
