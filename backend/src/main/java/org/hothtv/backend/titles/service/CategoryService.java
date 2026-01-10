package org.hothtv.backend.titles.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.titles.dto.CreateCategoryRequest;
import org.hothtv.backend.titles.model.Category;
import org.hothtv.backend.titles.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category createCategory(CreateCategoryRequest req) {
        Category category = new Category();
        category.setName(req.name());
        category.setSlug(req.slug());
        category.setDescription(req.description());
        category.setIsActive(req.isActive() != null ? req.isActive() : true);
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
