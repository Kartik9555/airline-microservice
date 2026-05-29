package com.learning.common.payload.response;

import com.learning.common.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private String ticketNumber;
    private TicketStatus status;
    private Long bookingId;
    private String bookingReference;
    private Long passengerId;
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerEmail;
    private Instant createdAt;
    private Instant updatedAt;
    private Long paymentId;
    private Double paymentAmount;
    private String paymentCurrency;
}
