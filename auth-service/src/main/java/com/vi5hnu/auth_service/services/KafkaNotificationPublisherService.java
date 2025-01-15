package com.vi5hnu.auth_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaNotificationPublisherService {
    private static final String TOPIC = "auth-notifications";
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendNotification(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
