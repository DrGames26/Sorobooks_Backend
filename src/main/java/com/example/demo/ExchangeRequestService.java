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
    private BookRepository bookRepository;  // Supondo que exista um repositório de livros

    public ExchangeRequestEntity createExchangeRequest(ExchangeRequestEntity request) {
        // Validar se os livros existem antes de criar a solicitação
        if (!bookRepository.existsById(request.getRequestedBookId()) || !bookRepository.existsById(request.getOfferedBookId())) {
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
        return exchangeRequestRepository.findByStatus(status);  // Método corrigido para receber ExchangeStatus
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
