package com.minh.user_service.DTOs;

/**
 * AccountCreatedMessageDTO is a Data Transfer Object that represents the
 * information of a newly created user account.
 */

public record AccountCreatedMessageDTO(String accountId,
                                       String username,
                                       String email,
                                       String fullName) {

}