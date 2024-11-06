package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeRequestController {

    private final ExchangeRequestService exchangeRequestService;

    public ExchangeRequestController(ExchangeRequestService exchangeRequestService) {
        this.exchangeRequestService = exchangeRequestService;
    }

    @PostMapping("/request")
    public ExchangeRequestEntity requestExchange(@RequestBody ExchangeRequestEntity request) {
        return exchangeRequestService.createExchangeRequest(request);
    }

    @GetMapping("/requests")
    public List<ExchangeRequestEntity> listExchangeRequests() {
        return exchangeRequestService.findAllExchangeRequests();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ExchangeRequestEntity> updateExchangeStatus(@PathVariable Long id, @RequestBody String status) {
        ExchangeRequestEntity updatedRequest = exchangeRequestService.updateStatus(id, status);
        if (updatedRequest != null) {
            return ResponseEntity.ok(updatedRequest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
