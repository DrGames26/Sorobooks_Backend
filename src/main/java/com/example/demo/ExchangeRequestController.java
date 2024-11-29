package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @PostMapping("/request")
    public ResponseEntity<?> requestExchange(@RequestBody ExchangeRequestEntity request) {
        if (request.getRequester() == null || request.getRequestedBook() == null || request.getOfferedBook() == null) {
            return ResponseEntity.badRequest().body("Os campos de usuário e livros são obrigatórios.");
        }

        if (!userService.findByEmail(request.getRequester()).isPresent()) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        ExchangeRequestEntity createdRequest = exchangeRequestService.createExchangeRequest(request);
        return ResponseEntity.status(201).body(createdRequest);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<ExchangeRequestEntity>> listExchangeRequests() {
        List<ExchangeRequestEntity> requests = exchangeRequestService.findAllExchangeRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ExchangeRequestEntity>> listPendingExchanges(Principal principal) {
        String email = principal.getName(); // Obtém o e-mail do usuário autenticado
        List<ExchangeRequestEntity> pendingRequests = exchangeRequestService.findPendingRequestsByUser(email);
        return ResponseEntity.ok(pendingRequests);
    }

    @GetMapping("/requests/for-owner")
    public ResponseEntity<List<ExchangeRequestEntity>> listRequestsForBookOwner(Principal principal) {
        String email = principal.getName(); // Obtém o e-mail do usuário autenticado
        List<ExchangeRequestEntity> requests = exchangeRequestService.findRequestsByRequestedBookOwner(email);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateExchangeStatus(@PathVariable Long id, @RequestBody Map<String, String> statusMap) {
        String statusValue = statusMap.get("status");

        if (statusValue == null || statusValue.isEmpty()) {
            return ResponseEntity.badRequest().body("O campo 'status' é obrigatório.");
        }

        ExchangeStatus exchangeStatus;
        try {
            exchangeStatus = ExchangeStatus.valueOf(statusValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Status inválido.");
        }

        ExchangeRequestEntity updatedRequest = exchangeRequestService.updateStatus(id, exchangeStatus);
        if (updatedRequest == null) {
            return ResponseEntity.notFound().build();
        }

        String message = "Sua solicitação de troca foi " + statusValue.toLowerCase() + ".";
        notificationService.createNotification(updatedRequest.getRequester(), message);

        return ResponseEntity.ok(updatedRequest);
    }
}
