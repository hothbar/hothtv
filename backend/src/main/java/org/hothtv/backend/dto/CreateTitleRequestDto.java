package org.hothtv.backend.dto;

import org.hothtv.backend.model.TitleTypeModel;

import java.time.LocalDate;

public record CreateTitleRequestDto(
        TitleTypeModel type,
        String name,
        LocalDate releaseDate,
        String description
) {}