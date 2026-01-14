package org.hothtv.backend.categories.dto;

public record CreateCategoryRequest(
        String name,
        String slug,
        String description,
        Boolean isActive
) {}
