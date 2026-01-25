package org.hothtv.backend.dto;

public record CreateEpisodeRequestDto(
        Integer episodeNumber,
        String name,
        Integer durationMinutes
) {}
