package com.example.backendapitaskmanager.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger core configuration.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
                .group("Tasks")
                .pathsToMatch("/api/taskmanager/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI();
    }

}
