package com.learning.booking.service.service;

import com.learning.booking.service.model.Booking;
import com.learning.booking.service.model.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> generateTicketsForBooking(Booking booking);
}
