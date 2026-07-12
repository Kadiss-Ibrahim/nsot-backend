package com.sielmed.nsotbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("NSoT Backend API")
                        .version("1.0.0")
                        .description("API de gestion de l'infrastructure réseau — Network Source of Truth (Stage PFA Sielmed)")
                        .license(new License()
                                .name("Sielmed - Usage interne")));
    }
}