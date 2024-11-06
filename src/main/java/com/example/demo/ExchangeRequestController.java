package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeRequestController {

    private final ExchangeRequestService exchangeRequestService;
    private final NotificationService notificationService;

    public ExchangeRequestController(ExchangeRequestService exchangeRequestService, NotificationService notificationService) {
        this.exchangeRequestService = exchangeRequestService;
        this.notificationService = notificationService;
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
            // Notificar o usuário que recebeu a troca
            String message = "Sua solicitação de troca foi " + status.toLowerCase() + ".";
            notificationService.createNotification(updatedRequest.getRequester(), message);
            return ResponseEntity.ok(updatedRequest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
