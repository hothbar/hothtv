package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.model.TitleCategoryModel;
import org.hothtv.backend.service.TitleCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/titles")
@RequiredArgsConstructor
public class TitleCategoryController {

    private final TitleCategoryService titleCategoryService;

    @PostMapping("/{titleId}/categories/{categoryId}")
    public ResponseEntity<Void> addCategoryToTitle(
            @PathVariable Long titleId,
            @PathVariable Long categoryId
    ) {
        titleCategoryService.addCategoryToTitle(titleId, categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{titleId}/categories")
    public ResponseEntity<List<TitleCategoryModel>> listCategoriesForTitle(@PathVariable Long titleId) {
        return ResponseEntity.ok(titleCategoryService.listLinksForTitle(titleId));
    }

    @DeleteMapping("/{titleId}/categories/{categoryId}")
    public ResponseEntity<Void> removeCategoryFromTitle(
            @PathVariable Long titleId,
            @PathVariable Long categoryId
    ) {
        titleCategoryService.removeCategoryFromTitle(titleId, categoryId);
        return ResponseEntity.noContent().build();
    }
}
