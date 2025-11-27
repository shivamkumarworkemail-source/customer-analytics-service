package com.devonbank.customer_analytics_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customerAnalyticsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customer Analytics Service")
                        .description("API documentation for DevOn Bank - Customer Analytics")
                        .version("1.0.0"));
    }
}
