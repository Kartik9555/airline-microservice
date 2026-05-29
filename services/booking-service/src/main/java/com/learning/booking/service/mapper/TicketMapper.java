package com.learning.booking.service.mapper;

import com.learning.booking.service.model.Ticket;
import com.learning.common.payload.response.TicketResponse;

public class TicketMapper {

    public static TicketResponse toTicket(Ticket ticket) {
        if (ticket == null) return null;
        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketNumber(ticket.getTicketNumber())
                .status(ticket.getStatus())
                .bookingId(ticket.getBooking() != null ? ticket.getBooking().getId() : null)
                .bookingReference(ticket.getBooking() != null ? ticket.getBooking().getBookingReference() : null)
                .passengerId(ticket.getPassenger() != null ? ticket.getPassenger().getId() : null)
                .passengerFirstName(ticket.getPassenger() != null ? ticket.getPassenger().getFirstName() : null)
                .passengerLastName(ticket.getPassenger() != null ? ticket.getPassenger().getLastName() : null)
                .passengerEmail(ticket.getPassenger() != null ? ticket.getPassenger().getEmail() : null)
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
}
