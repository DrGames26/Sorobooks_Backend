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
    public ResponseEntity<?> updateExchangeStatus(@PathVariable Long id, @RequestBody String status) {
        try {
            // Converter o status de String para ExchangeStatus
            ExchangeStatus exchangeStatus = ExchangeStatus.valueOf(status.toUpperCase());
            ExchangeRequestEntity updatedRequest = exchangeRequestService.updateStatus(id, exchangeStatus);
            if (updatedRequest == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar a solicitação
            }
            // Notificar o usuário que recebeu a troca
            String message = "Sua solicitação de troca foi " + status.toLowerCase() + ".";
            notificationService.createNotification(updatedRequest.getRequester(), message);
            return ResponseEntity.ok(updatedRequest);
        } catch (IllegalArgumentException e) {
            // Retornando erro 400 para status inválido
            return ResponseEntity.badRequest().body("Status inválido."); // Enviando mensagem de erro
        }
    }
}
