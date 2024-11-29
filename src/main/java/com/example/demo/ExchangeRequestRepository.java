package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequestEntity, Long> {
    List<ExchangeRequestEntity> findByStatusAndRequestedBookOwner(ExchangeStatus status, String ownerEmail);
    List<ExchangeRequestEntity> findByRequestedBookOwner(String ownerEmail);
}
