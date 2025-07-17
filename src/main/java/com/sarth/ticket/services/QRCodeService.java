package com.sarth.ticket.services;

import com.sarth.ticket.domain.entities.QRCode;
import com.sarth.ticket.domain.entities.Ticket;

import java.util.UUID;

public interface QRCodeService {

    QRCode generateQRCodes(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
