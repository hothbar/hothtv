package org.hothtv.backend.dto;

import java.time.LocalDate;

public record UpdateTitleRequestDto(
        String name,
        LocalDate releaseDate,
        String description
) {}
