package com.minh.auth_service.DTOs;

import lombok.Builder;

@Builder
public record CreatedAccountMessageDTO(String accountId,
                                       String username,
                                       String email,
                                       String fullName) {

}
