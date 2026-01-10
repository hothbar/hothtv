package org.hothtv.backend.people.dto;

import java.time.LocalDate;

public record CreatePersonRequest(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        LocalDate dateOfDeath
) {}
