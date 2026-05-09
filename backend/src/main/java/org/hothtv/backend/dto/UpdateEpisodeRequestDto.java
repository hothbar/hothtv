package org.hothtv.backend.dto;

public record UpdateEpisodeRequestDto(
        String name,
        Integer durationMinutes
) {}
