package org.hothtv.backend.users.dto;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {}
