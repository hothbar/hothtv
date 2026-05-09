package org.hothtv.backend.dto;

import java.math.BigDecimal;

public record UpdatePlanRequestDto(
        String name,
        BigDecimal price,
        Integer durationDays
) {}
