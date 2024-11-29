package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequestEntity, Long> {
    List<ExchangeRequestEntity> findByStatus(ExchangeStatus status);

    @Query("SELECT e FROM ExchangeRequestEntity e WHERE e.requestedBook.usuarioPublicador = :email")
    List<ExchangeRequestEntity> findRequestsByRequestedBookOwner(@Param("email") String email);
}
