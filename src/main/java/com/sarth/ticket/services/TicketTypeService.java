package com.sarth.ticket.services;

import com.sarth.ticket.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
