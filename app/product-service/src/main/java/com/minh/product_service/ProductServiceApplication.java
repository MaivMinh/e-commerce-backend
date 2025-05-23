package com.minh.product_service;

import com.minh.product_service.config.AxonConfig;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableJpaRepositories(basePackages = "com.minh.product_service.repository")
@EntityScan(value = "com.minh.product_service.entity")
@Import(AxonConfig.class)
public class ProductServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductServiceApplication.class, args);
  }

  @Autowired
  public void configure(EventProcessingConfigurer config) {
    config.registerListenerInvocationErrorHandler("product-group",
            conf -> PropagatingErrorHandler.instance());
  }
}