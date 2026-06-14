package com.learning.booking.service.model;

import com.learning.common.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String email;
    private String phone;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String passportNumber;

    private String nationality;

    private String frequentFlyerNumber;

    private Long primaryUserId;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = true)
    private Booking booking;

    @Builder.Default
    private Boolean isActive = true;

    @Version
    private Long version;

    private Boolean requiresWheelchairAssistance;
    private String dietaryPreferences;
    private String medicalConditions;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public boolean isAdult() {
        return getAge() >= 18;
    }
}