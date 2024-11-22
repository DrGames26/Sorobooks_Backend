package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_requests")
public class ExchangeRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requested_book_id", nullable = false)
    private BookEntity requestedBook;

    @ManyToOne
    @JoinColumn(name = "offered_book_id", nullable = false)
    private BookEntity offeredBook;

    @Column(name = "requester", nullable = false)
    private String requester;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ExchangeStatus status = ExchangeStatus.PENDING;

    @Column(name = "requested_date", nullable = false)
    private LocalDateTime requestedDate = LocalDateTime.now();

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookEntity getRequestedBook() {
        return requestedBook;
    }

    public void setRequestedBook(BookEntity requestedBook) {
        this.requestedBook = requestedBook;
    }

    public BookEntity getOfferedBook() {
        return offeredBook;
    }

    public void setOfferedBook(BookEntity offeredBook) {
        this.offeredBook = offeredBook;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getPhoneNumber() {
        return phoneNumber;  // Getter para o número de telefone
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;  // Setter para o número de telefone
    }

    public ExchangeStatus getStatus() {
        return status;
    }

    public void setStatus(ExchangeStatus status) {
        this.status = status;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }
}
