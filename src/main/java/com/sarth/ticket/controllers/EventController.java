package com.sarth.ticket.controllers;

import com.sarth.ticket.domain.CreateEventRequest;
import com.sarth.ticket.domain.dtos.CreateEventRequestDto;
import com.sarth.ticket.domain.dtos.CreateEventResponseDto;
import com.sarth.ticket.domain.dtos.EventSummaryDto;
import com.sarth.ticket.domain.entities.Event;
import com.sarth.ticket.mappers.EventMapper;
import com.sarth.ticket.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto
            ){

        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId = UUID.fromString(jwt.getSubject());

        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<EventSummaryDto>> listEvents(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        Pageable pageable = PageRequest.of(page, size);
        
        Page<Event> events = eventService.listEvents(userId, pageable);
        Page<EventSummaryDto> eventSummaryDtos = events.map(eventMapper::toSummaryDto);
        
        return ResponseEntity.ok(eventSummaryDtos);
    }

}
