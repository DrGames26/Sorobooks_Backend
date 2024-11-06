package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_requests")
public class ExchangeRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requested_book_id", nullable = false)
    private Long requestedBookId;

    @Column(name = "offered_book_id", nullable = false)
    private Long offeredBookId;

    @Column(name = "requester", nullable = false)
    private String requester; // Usuário que está solicitando a troca

    @Column(name = "status", nullable = false)
    private String status = "PENDING"; // Status inicial como "PENDING"

    @Column(name = "requested_date", nullable = false)
    private LocalDateTime requestedDate = LocalDateTime.now();

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestedBookId() {
        return requestedBookId;
    }

    public void setRequestedBookId(Long requestedBookId) {
        this.requestedBookId = requestedBookId;
    }

    public Long getOfferedBookId() {
        return offeredBookId;
    }

    public void setOfferedBookId(Long offeredBookId) {
        this.offeredBookId = offeredBookId;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }
}
