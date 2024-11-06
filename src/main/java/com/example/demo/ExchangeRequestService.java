package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRequestService {

    @Autowired
    private ExchangeRequestRepository exchangeRequestRepository;

    public ExchangeRequestEntity createExchangeRequest(ExchangeRequestEntity request) {
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
            request.setStatus(status);
            return exchangeRequestRepository.save(request);
        }
        return null; // ou lançar uma exceção, se preferir
    }
}
