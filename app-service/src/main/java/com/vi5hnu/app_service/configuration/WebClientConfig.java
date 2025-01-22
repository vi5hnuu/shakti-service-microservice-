package com.vi5hnu.app_service.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // Enables LoadBalancer integration
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
