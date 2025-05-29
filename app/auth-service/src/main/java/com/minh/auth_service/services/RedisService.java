package com.minh.auth_service.services;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class RedisService {
  private final RedisOperations<String, String> redisOperations;

  public String get(String key) {
    return redisOperations.opsForValue().get(key);
  }

  public void set(String key, String value, long ttl) {
    redisOperations.opsForValue().set(key, value, Duration.ofSeconds(ttl));
  }
}
