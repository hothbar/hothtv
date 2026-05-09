package org.hothtv.backend.dto;

import java.time.LocalDate;

public record UpdatePersonRequestDto(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        LocalDate dateOfDeath
) {}
