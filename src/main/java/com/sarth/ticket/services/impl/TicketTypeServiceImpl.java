package com.sarth.ticket.services.impl;

import com.sarth.ticket.domain.entities.Ticket;
import com.sarth.ticket.domain.entities.TicketStatusEnum;
import com.sarth.ticket.domain.entities.TicketType;
import com.sarth.ticket.domain.entities.User;
import com.sarth.ticket.exceptions.TicketTypeNotFoundException;
import com.sarth.ticket.exceptions.TicketsSoldOutException;
import com.sarth.ticket.exceptions.UserNotFoundException;
import com.sarth.ticket.repositories.TicketRepository;
import com.sarth.ticket.repositories.TicketTypeRepository;
import com.sarth.ticket.repositories.UserRepository;
import com.sarth.ticket.services.QRCodeService;
import com.sarth.ticket.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QRCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %s was not found", userId)
        ));

        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException(
                        String.format("Ticket type with ID %s was not found", ticketTypeId)
                ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();


        if (purchasedTickets + 1 > totalAvailable) {
            throw new TicketsSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQRCodes(savedTicket);

        return ticketRepository.save(savedTicket);

    }
}


























