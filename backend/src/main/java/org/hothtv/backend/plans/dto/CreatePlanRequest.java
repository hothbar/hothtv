package org.hothtv.backend.plans.dto;

import java.math.BigDecimal;

public record CreatePlanRequest(
        String name,
        BigDecimal price,
        Integer durationDays
) {}
