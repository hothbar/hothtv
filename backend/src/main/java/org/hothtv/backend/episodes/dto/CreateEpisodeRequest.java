package org.hothtv.backend.episodes.dto;

public record CreateEpisodeRequest(
        Integer episodeNumber,
        String name,
        Integer durationMinutes
) {}
