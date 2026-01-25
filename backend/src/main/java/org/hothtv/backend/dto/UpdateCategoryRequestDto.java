package org.hothtv.backend.dto;

import jakarta.validation.constraints.Size;

public record UpdateCategoryRequestDto(
        @Size(max = 100) String name,
        @Size(max = 120) String slug,
        @Size(max = 5000) String description,
        Boolean isActive
) {}
