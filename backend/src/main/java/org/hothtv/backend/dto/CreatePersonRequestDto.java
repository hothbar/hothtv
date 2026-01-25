package org.hothtv.backend.dto;

import java.time.LocalDate;

public record CreatePersonRequestDto(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        LocalDate dateOfDeath
) {}
