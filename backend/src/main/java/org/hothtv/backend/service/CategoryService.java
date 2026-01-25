package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreateCategoryRequestDto;
import org.hothtv.backend.dto.UpdateCategoryRequestDto;
import org.hothtv.backend.model.CategoryModel;
import org.hothtv.backend.repository.CategoryRepository;
import org.hothtv.backend.common.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryModel> list() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CategoryModel get(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found: " + id));
    }

    @Transactional
    public CategoryModel create(CreateCategoryRequestDto req) {
        if (categoryRepository.existsBySlug(req.slug())) {
            throw new IllegalArgumentException("Category slug already exists: " + req.slug());
        }

        CategoryModel c = new CategoryModel();
        c.setName(req.name());
        c.setSlug(req.slug());
        c.setDescription(req.description());
        c.setIsActive(true);

        return categoryRepository.save(c);
    }

    @Transactional
    public CategoryModel update(Long id, UpdateCategoryRequestDto req) {
        CategoryModel c = get(id);

        if (req.slug() != null && !req.slug().equals(c.getSlug())) {
            if (categoryRepository.existsBySlug(req.slug())) {
                throw new IllegalArgumentException("Category slug already exists: " + req.slug());
            }
            c.setSlug(req.slug());
        }

        if (req.name() != null) c.setName(req.name());
        if (req.description() != null) c.setDescription(req.description());
        if (req.isActive() != null) c.setIsActive(req.isActive());

        return categoryRepository.save(c);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
