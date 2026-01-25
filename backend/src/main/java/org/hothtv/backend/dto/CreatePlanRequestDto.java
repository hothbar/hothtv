package org.hothtv.backend.dto;

import java.math.BigDecimal;

public record CreatePlanRequestDto(
        String name,
        BigDecimal price,
        Integer durationDays
) {}
