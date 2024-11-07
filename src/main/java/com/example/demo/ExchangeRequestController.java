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

    @PostMapping("/request")
    public ResponseEntity<?> requestExchange(@RequestBody ExchangeRequestEntity request) {
        // Verifica se o usuário está autenticado (verifique o login do usuário, por exemplo, pelo contexto de segurança)
        if (request.getRequester() == null || request.getRequestedBookId() == null || request.getOfferedBookId() == null) {
            return ResponseEntity.badRequest().body("Os campos de usuário e livros são obrigatórios.");
        }

        // Verifica se o usuário existe
        if (!userService.findByEmail(request.getRequester()).isPresent()) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        // Cria a solicitação de troca
        ExchangeRequestEntity createdRequest = exchangeRequestService.createExchangeRequest(request);
        return ResponseEntity.status(201).body(createdRequest); // Retornando 201 (Created)
    }

    @GetMapping("/requests")
    public ResponseEntity<List<ExchangeRequestEntity>> listExchangeRequests() {
        List<ExchangeRequestEntity> requests = exchangeRequestService.findAllExchangeRequests();
        return ResponseEntity.ok(requests);
    }

    // Novo endpoint para listar solicitações pendentes
    @GetMapping("/pending")
    public ResponseEntity<List<ExchangeRequestEntity>> listPendingRequests() {
        List<ExchangeRequestEntity> pendingRequests = exchangeRequestService.findRequestsByStatus(ExchangeStatus.PENDING);
        return ResponseEntity.ok(pendingRequests); // Usando o enum ExchangeStatus
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateExchangeStatus(@PathVariable Long id, @RequestBody Map<String, String> statusMap) {
        try {
            // Extrair o valor do campo 'status' do JSON
            String statusValue = statusMap.get("status");

            // Verifica se o status enviado é válido
            if (statusValue == null || statusValue.isEmpty()) {
                return ResponseEntity.badRequest().body("O campo 'status' é obrigatório.");
            }

            // Valida se o status é um valor permitido
            ExchangeStatus exchangeStatus;
            try {
                exchangeStatus = ExchangeStatus.valueOf(statusValue.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Status inválido."); // Retorna erro 400 para status inválido
            }

            // Atualiza o status da solicitação
            ExchangeRequestEntity updatedRequest = exchangeRequestService.updateStatus(id, exchangeStatus);
            if (updatedRequest == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar a solicitação
            }

            // Notifica o usuário que recebeu a troca
            String message = "Sua solicitação de troca foi " + statusValue.toLowerCase() + ".";
            notificationService.createNotification(updatedRequest.getRequester(), message);

            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao atualizar o status da solicitação.");
        }
    }
}
