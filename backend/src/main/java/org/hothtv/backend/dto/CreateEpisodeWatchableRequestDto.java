package org.hothtv.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateEpisodeWatchableRequestDto(
        @NotNull @Positive Long episodeId
) {}
