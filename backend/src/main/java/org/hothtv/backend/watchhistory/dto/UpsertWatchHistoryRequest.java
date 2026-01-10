package org.hothtv.backend.watchhistory.dto;

public record UpsertWatchHistoryRequest(
        Long userId,
        Long watchableId,
        Integer progressSeconds,
        Boolean completed
) {}
