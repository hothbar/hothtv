package org.hothtv.backend.watchables.dto;

import org.hothtv.backend.watchables.model.WatchableKind;

public record WatchableDetailsResponse(
        Long watchableId,
        WatchableKind kind,
        Long singleTitleId,   // only for SINGLE
        Long episodeId        // only for EPISODE
) {}
