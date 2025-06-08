package com.minh.user_service.DTOs;

import lombok.Builder;

@Builder
public record ActiveUserMessageDTO(String accountId) {
}
