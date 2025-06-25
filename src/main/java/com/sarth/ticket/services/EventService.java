package com.sarth.ticket.services;

import com.sarth.ticket.domain.CreateEventRequest;
import com.sarth.ticket.domain.entities.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
}
