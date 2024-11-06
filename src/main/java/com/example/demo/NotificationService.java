package com.example.demo;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public Notification createNotification(String userEmail, String message) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);  // Associando o usuário à notificação
        notification.setMessage(message);  // Atribuindo a mensagem à notificação
        notification.setCreatedAt(LocalDateTime.now());  // Definindo o timestamp de criação

        return notificationRepository.save(notification);  // Salvando a notificação no banco
    }
}
