package com.minh.auth_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
  @Value("${BRAVO_API_KEY}")
  private String apiKey;

  @Bean
  public RestClient restClient() {
    return RestClient.builder()
            .baseUrl("https://api.brevo.com")
            .defaultHeader("accept", "application/json")
            .defaultHeader("content-type", "application/json")
            .defaultHeader("api-key", apiKey)
            .build();
  }
}
