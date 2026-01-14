package org.hothtv.backend.watchhistory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpsertWatchHistoryRequest(
        @NotNull @Positive Long userId,
        @NotNull @Positive Long watchableId,
        @Min(0) int progressSeconds,
        boolean completed
) {}
