package org.hothtv.backend.titles.dto;

import org.hothtv.backend.titles.model.TitleType;

import java.time.LocalDate;

public record CreateTitleRequest(
        TitleType type,
        String name,
        LocalDate releaseDate,
        String description
) {}