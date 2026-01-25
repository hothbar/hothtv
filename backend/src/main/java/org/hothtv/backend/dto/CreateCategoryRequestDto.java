package org.hothtv.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequestDto(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 120) String slug,
        @Size(max = 5000) String description
) {}
