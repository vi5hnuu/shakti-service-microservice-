package com.vi5hnu.notification_service.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaNotificationConsumerService {

    @KafkaListener(topics = "auth-notifications", groupId = "notification-service-group")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
        // Process the notification (e.g., send email, SMS, etc.)
    }
}
