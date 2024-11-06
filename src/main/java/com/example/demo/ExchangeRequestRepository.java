package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequestEntity, Long> {

    // Método para buscar solicitações com status específico
    List<ExchangeRequestEntity> findByStatus(ExchangeStatus status);
}
