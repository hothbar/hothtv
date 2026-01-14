package org.hothtv.backend.watchables.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateEpisodeWatchableRequest(
        @NotNull @Positive Long episodeId
) {}
