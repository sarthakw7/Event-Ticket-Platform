package com.sarth.ticket.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sarth.ticket.domain.entities.QRCode;
import com.sarth.ticket.domain.entities.QRCodeStatusEnum;
import com.sarth.ticket.domain.entities.Ticket;
import com.sarth.ticket.exceptions.QRCodeGenerationException;
import com.sarth.ticket.repositories.QRCodeRepository;
import com.sarth.ticket.services.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private final QRCodeWriter qrCodeWriter;
    private final QRCodeRepository qrCodeRepository;

    @Override
    public QRCode generateQRCodes(Ticket ticket) {
        try {
            UUID uniqueId = UUID.randomUUID();
            String qrCodeImage = generateQRCodeImage(uniqueId);

            QRCode qrCode = new QRCode();
            qrCode.setId(uniqueId);
            qrCode.setStatus(QRCodeStatusEnum.ACTIVE);
            qrCode.setValue(qrCodeImage);
            qrCode.setTicket(ticket);

            return qrCodeRepository.saveAndFlush(qrCode);

        } catch (IOException | WriterException ex) {

            throw new QRCodeGenerationException("Failed to generate QR Code", ex);
        }
    }

    private String generateQRCodeImage(UUID uniqueId) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT
        );

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(qrCodeImage, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }


    }


}
