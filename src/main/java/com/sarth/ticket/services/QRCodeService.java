package com.sarth.ticket.services;

import com.sarth.ticket.domain.entities.QRCode;
import com.sarth.ticket.domain.entities.Ticket;

public interface QRCodeService {

    QRCode generateQRCodes(Ticket ticket);
}
