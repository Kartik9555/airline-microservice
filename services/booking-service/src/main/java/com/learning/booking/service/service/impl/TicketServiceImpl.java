package com.learning.booking.service.service.impl;

import com.learning.booking.service.model.Booking;
import com.learning.booking.service.model.Passenger;
import com.learning.booking.service.model.Ticket;
import com.learning.booking.service.repository.TicketRepository;
import com.learning.booking.service.service.TicketService;
import com.learning.common.enums.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> generateTicketsForBooking(Booking booking) {
        List<Ticket> tickets = new ArrayList<>();
        for(Passenger passenger : booking.getPassengers()) {
            final Ticket ticket = Ticket.builder()
                    .ticketNumber(generateUniqueTicketNumber())
                    .status(TicketStatus.BOOKED)
                    .issueDate(LocalDateTime.now())
                    .passenger(passenger)
                    .booking(booking)
                    .build();

            final Ticket saved = ticketRepository.save(ticket);
            tickets.add(saved);
        }
        return tickets;
    }

    private String generateUniqueTicketNumber() {
        String ticketNumber;
        do {
            String datePart = LocalDateTime.now().toString().substring(0, 10);
            String randomPart = UUID.randomUUID().toString().substring(0, 8);
            ticketNumber = String.format("TKT-%s-%s", datePart, randomPart);
        } while (ticketRepository.existsByTicketNumber(ticketNumber));
        return ticketNumber;
    }
}
