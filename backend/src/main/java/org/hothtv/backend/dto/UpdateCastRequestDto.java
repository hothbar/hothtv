package org.hothtv.backend.dto;

public record UpdateCastRequestDto(
        String characterName,
        Integer billingOrder
) {}
