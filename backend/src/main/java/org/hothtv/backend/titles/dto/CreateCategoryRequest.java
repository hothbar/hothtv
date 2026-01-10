package org.hothtv.backend.titles.dto;

public record CreateCategoryRequest(
        String name,
        String slug,
        String description,
        Boolean isActive
) {}
