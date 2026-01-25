package org.hothtv.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreateCategoryRequestDto;
import org.hothtv.backend.dto.UpdateCategoryRequestDto;
import org.hothtv.backend.model.CategoryModel;
import org.hothtv.backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryModel> list() {
        return categoryService.list();
    }

    @GetMapping("/{id}")
    public CategoryModel get(@PathVariable Long id) {
        return categoryService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryModel create(@Valid @RequestBody CreateCategoryRequestDto req) {
        return categoryService.create(req);
    }

    @PutMapping("/{id}")
    public CategoryModel update(@PathVariable Long id, @Valid @RequestBody UpdateCategoryRequestDto req) {
        return categoryService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
