package com.sarth.ticket.repositories;

import com.sarth.ticket.domain.entities.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, UUID> {
}
