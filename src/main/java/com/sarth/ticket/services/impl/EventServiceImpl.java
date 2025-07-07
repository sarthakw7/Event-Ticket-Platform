package com.sarth.ticket.services.impl;

import com.sarth.ticket.domain.CreateEventRequest;
import com.sarth.ticket.domain.entities.Event;
import com.sarth.ticket.domain.entities.TicketType;
import com.sarth.ticket.domain.entities.User;
import com.sarth.ticket.exceptions.UserNotFoundException;
import com.sarth.ticket.repositories.EventRepository;
import com.sarth.ticket.repositories.UserRepository;
import com.sarth.ticket.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {


    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer =  userRepository.findById(organizerId)
                        .orElseThrow(() -> new UserNotFoundException(
                                String.format("User with ID '%s' not found", organizerId)
                        ));

        Event evenToCreate = new Event();

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                        TicketType ticketTypeToCreate = new TicketType();
                        ticketTypeToCreate.setName(ticketType.getName());
                        ticketTypeToCreate.setPrice(ticketType.getPrice());
                        ticketTypeToCreate.setDescription(ticketType.getDescription());
                        ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                        ticketTypeToCreate.setEvent(evenToCreate);
                        return ticketTypeToCreate;
                }).toList();


        evenToCreate.setName(event.getName());
        evenToCreate.setStart(event.getStart());
        evenToCreate.setEnd(event.getEnd());
        evenToCreate.setVenue(event.getVenue());
        evenToCreate.setSalesStart(event.getSalesStart());
        evenToCreate.setSalesEnd(event.getSalesEnd());
        evenToCreate.setStatus(event.getStatus());
        evenToCreate.setOrganizer(organizer);
        evenToCreate.setTicketTypes(ticketTypesToCreate);


        return eventRepository.save(evenToCreate);
    }

    @Override
    public Page<Event> listEvents(UUID organizerId, Pageable pageable) {
        // Verify the user exists
        userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizerId)
                ));
        
        // Return events for the organizer with pagination
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }
}
