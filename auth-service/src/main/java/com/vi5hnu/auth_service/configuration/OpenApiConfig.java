package com.vi5hnu.auth_service.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI registrationOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spirtual Shakti API")
                        .description("you gateway to shakti dhuun")
                        .version("1.0"));
    }
}