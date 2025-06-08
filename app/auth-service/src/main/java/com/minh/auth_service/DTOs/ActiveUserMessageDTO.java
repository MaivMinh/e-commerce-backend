package com.minh.auth_service.DTOs;

import lombok.Builder;

@Builder
public record ActiveUserMessageDTO(String accountId) {
}
