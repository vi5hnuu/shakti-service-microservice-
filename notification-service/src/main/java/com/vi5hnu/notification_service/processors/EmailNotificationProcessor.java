package com.vi5hnu.notification_service.processors;

import com.vi5hnu.notification_service.configurations.SchedulerConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnExpression("${cron.enabled:true} && ${cron.email-notification-processor.enabled:true}")
@RequiredArgsConstructor
public class EmailNotificationProcessor {
    private final SchedulerConfig schedulerConfig;

    @PostConstruct
    public void logConfig() {
        System.out.println("Cron enabled: " + schedulerConfig.isEnabled());
        System.out.println("Email Notification Processor Period: " + schedulerConfig.getEmailNotificationProcessor().getPeriod());
    }

    @Scheduled(cron = "${cron.email-notification-processor.period}")
    public void emailNotificationVerificationExpiry() {
        try {
            if (schedulerConfig.getEmailNotificationProcessor().isEnabled()) {
                scheduleNotificationExpiryProcessor();
            }
        } catch (Exception e) {
            log.info("Error in email notification processor" + e.getMessage());
        }
    }

    private void scheduleNotificationExpiryProcessor() {
        String cron = schedulerConfig.getEmailNotificationProcessor().getPeriod();
        System.out.println("Scheduling notification expiry processor with cron: " + cron);
        // Add task execution logic here
    }
}
