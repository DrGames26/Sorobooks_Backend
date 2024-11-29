package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRequestService {

    private final ExchangeRequestRepository exchangeRequestRepository;

    public ExchangeRequestService(ExchangeRequestRepository exchangeRequestRepository) {
        this.exchangeRequestRepository = exchangeRequestRepository;
    }

    public ExchangeRequestEntity createExchangeRequest(ExchangeRequestEntity request) {
        return exchangeRequestRepository.save(request);
    }

    public List<ExchangeRequestEntity> findAllExchangeRequests() {
        return exchangeRequestRepository.findAll();
    }

    public List<ExchangeRequestEntity> findPendingRequestsByUser(String email) {
        return exchangeRequestRepository.findByStatusAndRequestedBookOwner(ExchangeStatus.PENDING, email);
    }

    public List<ExchangeRequestEntity> findRequestsByRequestedBookOwner(String email) {
        return exchangeRequestRepository.findByRequestedBookOwner(email);
    }

    public ExchangeRequestEntity updateStatus(Long id, ExchangeStatus status) {
        return exchangeRequestRepository.findById(id).map(request -> {
            request.setStatus(status);
            return exchangeRequestRepository.save(request);
        }).orElse(null);
    }
}
