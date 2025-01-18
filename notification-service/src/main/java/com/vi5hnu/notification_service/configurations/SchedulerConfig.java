package com.vi5hnu.notification_service.configurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "cron")
@NoArgsConstructor
public class SchedulerConfig {
    private boolean enabled;
    private TaskConfiguration emailNotificationProcessor;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TaskConfiguration {
        private final boolean enabled;
        private final String initialDelay;
        private final String period;
        private final int batchSize;
        private final int threadPool;
    }
}
