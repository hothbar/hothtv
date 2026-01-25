package org.hothtv.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSingleWatchableRequestDto(
        @NotNull @Positive Long singleTitleId
) {}
