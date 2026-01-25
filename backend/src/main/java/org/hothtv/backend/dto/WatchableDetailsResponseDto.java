package org.hothtv.backend.dto;

import org.hothtv.backend.model.WatchableKindModel;

public record WatchableDetailsResponseDto(
        Long watchableId,
        WatchableKindModel kind,
        Long singleTitleId,   // only for SINGLE
        Long episodeId        // only for EPISODE
) {}
