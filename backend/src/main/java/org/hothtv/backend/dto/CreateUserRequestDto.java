package org.hothtv.backend.dto;

public record CreateUserRequestDto(
        String firstName,
        String lastName,
        String email,
        String password
) {}
