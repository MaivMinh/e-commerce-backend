package com.minh.api_gateway.filter;

import com.minh.api_gateway.service.AuthService;
import com.minh.grpc_service.auth.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {
  private final AuthService authService;

  @Override
  public int getOrder() {
    return -1;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (exchange.getRequest().getURI().getPath().startsWith("/api/auth")) {
      return chain.filter(exchange);
    }
    String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
    if (authorization != null) {
      String token = authorization.substring(7);
      AuthResponse response = authService.authenticate(token);
      if (!response.getIsValid()) {
        log.warn("Invalid token: {}", token);
        exchange.getResponse().setRawStatusCode(response.getStatus());
        return exchange.getResponse().setComplete();
      } else {
        log.info("Token is valid: {}", token);
        // Optionally, you can set the user information in the exchange attributes
        String accountId = response.getAuthInfo().getAccountId();
        String role = response.getAuthInfo().getRole();
        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header("ACCOUNT-ID", accountId)
                .header("ROLE", role)
                .build();
        ServerWebExchange updated = exchange.mutate().request(request).build();
        return chain.filter(updated);
      }
    }
    return chain.filter(exchange);
  }
}