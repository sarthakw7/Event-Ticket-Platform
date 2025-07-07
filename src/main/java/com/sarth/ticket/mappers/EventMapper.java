package com.sarth.ticket.mappers;

import com.sarth.ticket.domain.CreateEventRequest;
import com.sarth.ticket.domain.CreateTicketTypeRequest;
import com.sarth.ticket.domain.dtos.CreateEventRequestDto;
import com.sarth.ticket.domain.dtos.CreateEventResponseDto;
import com.sarth.ticket.domain.dtos.CreateTicketTypeRequestDto;
import com.sarth.ticket.domain.dtos.EventSummaryDto;
import com.sarth.ticket.domain.dtos.TicketTypeSummaryDto;
import com.sarth.ticket.domain.entities.Event;
import com.sarth.ticket.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    EventSummaryDto toSummaryDto(Event event);

    TicketTypeSummaryDto toSummaryDto(TicketType ticketType);

}
