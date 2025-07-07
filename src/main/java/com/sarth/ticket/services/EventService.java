package com.sarth.ticket.services;

import com.sarth.ticket.domain.CreateEventRequest;
import com.sarth.ticket.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
    Page<Event> listEvents(UUID organizerId, Pageable pageable);
}
