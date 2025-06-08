package com.minh.auth_service.DTOs;

import lombok.Builder;

@Builder
public record UserCreatedMessageDTO(String accountId, String userId) {
}
