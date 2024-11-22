package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeRequestController {

    private final ExchangeRequestService exchangeRequestService;
    private final NotificationService notificationService;
    private final UserService userService;

    public ExchangeRequestController(ExchangeRequestService exchangeRequestService,
                                     NotificationService notificationService,
                                     UserService userService) {
        this.exchangeRequestService = exchangeRequestService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    // Endpoint para aceitar a troca
    @PutMapping("/accept/{id}")
    public ResponseEntity<?> acceptExchange(@PathVariable Long id) {
        try {
            // Lógica para aceitar a troca
            ExchangeRequestEntity updatedRequest = exchangeRequestService.updateStatus(id, ExchangeStatus.ACCEPTED);
            if (updatedRequest == null) {
                return ResponseEntity.notFound().build();
            }

            // Notifica o usuário que a troca foi aceita
            String message = "Sua solicitação de troca foi aceita.";
            notificationService.createNotification(updatedRequest.getRequester(), message);

            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao aceitar a troca.");
        }
    }

    // Endpoint para recusar a troca
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectExchange(@PathVariable Long id) {
        try {
            // Lógica para recusar a troca
            ExchangeRequestEntity updatedRequest = exchangeRequestService.updateStatus(id, ExchangeStatus.REJECTED);
            if (updatedRequest == null) {
                return ResponseEntity.notFound().build();
            }

            // Notifica o usuário que a troca foi recusada
            String message = "Sua solicitação de troca foi recusada.";
            notificationService.createNotification(updatedRequest.getRequester(), message);

            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao recusar a troca.");
        }
    }
}

