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

    public ExchangeRequestEntity updateStatus(Long id, String status) {
        Optional<ExchangeRequestEntity> optionalRequest = exchangeRequestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            ExchangeRequestEntity request = optionalRequest.get();

            try {
                ExchangeStatus exchangeStatus = ExchangeStatus.valueOf(status);
                request.setStatus(exchangeStatus);
            } catch (IllegalArgumentException e) {
                // Retornando uma exceção mais detalhada
                throw new IllegalArgumentException("Status inválido: " + status);
            }

            return exchangeRequestRepository.save(request);
        }
        return null;
    }

    // Novo método para encontrar solicitações com status específico
    public List<ExchangeRequestEntity> findRequestsByStatus(String status) {
        // Assume-se que ExchangeRequestRepository tem um método para buscar por status
        return exchangeRequestRepository.findByStatus(ExchangeStatus.valueOf(status));
    }
}
