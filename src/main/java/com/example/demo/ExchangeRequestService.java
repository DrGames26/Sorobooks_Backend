package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRequestService {

    @Autowired
    private ExchangeRequestRepository exchangeRequestRepository;

    @Autowired
    private BookRepository bookRepository;

    public ExchangeRequestEntity createExchangeRequest(ExchangeRequestEntity request) {
        if (request.getRequestedBook() == null || request.getOfferedBook() == null
                || !bookRepository.existsById(request.getRequestedBook().getId())
                || !bookRepository.existsById(request.getOfferedBook().getId())) {
            throw new RuntimeException("Um ou mais livros não existem.");
        }
        return exchangeRequestRepository.save(request);
    }

    public List<ExchangeRequestEntity> findAllExchangeRequests() {
        return exchangeRequestRepository.findAll();
    }

    public Optional<ExchangeRequestEntity> findById(Long id) {
        return exchangeRequestRepository.findById(id);
    }

    public List<ExchangeRequestEntity> findRequestsByStatus(ExchangeStatus status) {
        return exchangeRequestRepository.findByStatus(status);
    }

    public List<ExchangeRequestEntity> findRequestsByRequestedBookOwner(String email) {
        return exchangeRequestRepository.findRequestsByRequestedBookOwner(email);
    }

    public ExchangeRequestEntity updateStatus(Long id, ExchangeStatus status) {
        Optional<ExchangeRequestEntity> optionalRequest = exchangeRequestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            ExchangeRequestEntity request = optionalRequest.get();
            request.setStatus(status);
            return exchangeRequestRepository.save(request);
        }
        return null;
    }
}
