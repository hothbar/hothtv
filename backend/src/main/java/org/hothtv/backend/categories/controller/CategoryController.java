package org.hothtv.backend.categories.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.categories.dto.CreateCategoryRequest;
import org.hothtv.backend.categories.model.Category;
import org.hothtv.backend.titles.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // GET /api/categories
    @GetMapping
    public List<Category> list() {
        return categoryService.listCategories();
    }

    // POST /api/categories
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody CreateCategoryRequest req) {
        return categoryService.createCategory(req);
    }

    // DELETE /api/categories/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
