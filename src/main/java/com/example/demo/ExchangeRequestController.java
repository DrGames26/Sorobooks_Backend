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
    public ResponseEntity<ExchangeRequestEntity> requestExchange(@RequestBody ExchangeRequestEntity request) {
        ExchangeRequestEntity createdRequest = exchangeRequestService.createExchangeRequest(request);
        return ResponseEntity.status(201).body(createdRequest); // Retornando 201 (Created)
    }

    @GetMapping("/requests")
    public List<ExchangeRequestEntity> listExchangeRequests() {
        return exchangeRequestService.findAllExchangeRequests();
    }

    // Novo endpoint para listar solicitações pendentes
    @GetMapping("/pending")
    public List<ExchangeRequestEntity> listPendingRequests() {
        return exchangeRequestService.findRequestsByStatus(ExchangeStatus.PENDING); // Usando o enum
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ExchangeRequestEntity> updateExchangeStatus(@PathVariable Long id, @RequestBody String status) {
        try {
            ExchangeStatus exchangeStatus = ExchangeStatus.valueOf(status.toUpperCase());
            ExchangeRequestEntity updatedRequest = exchangeRequestService.updateStatus(id, exchangeStatus);
            String message = "Sua solicitação de troca foi " + status.toLowerCase() + ".";
            notificationService.createNotification(updatedRequest.getRequester(), message);
            return ResponseEntity.ok(updatedRequest);
        } catch (IllegalArgumentException e) {
            // Detalhando o erro para o usuário
            return ResponseEntity.badRequest().body("Status inválido. Valores permitidos: PENDING, ACCEPTED, REJECTED, COMPLETED.");
        }
    }
}
