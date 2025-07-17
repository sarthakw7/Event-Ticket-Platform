package com.sarth.ticket.repositories;

import com.sarth.ticket.domain.entities.QRCode;
import com.sarth.ticket.domain.entities.QRCodeStatusEnum;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, UUID> {

    @EntityGraph(attributePaths = {"ticket", "ticket.purchaser"})
    Optional<QRCode> findByTicket_IdAndTicket_Purchaser_Id(UUID ticketId, UUID purchaserId);

    Optional<QRCode> findByIdAndStatus(UUID id, QRCodeStatusEnum status);
}
