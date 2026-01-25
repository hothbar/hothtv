package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.repository.CategoryRepository;
import org.hothtv.backend.repository.TitleRepository;
import org.hothtv.backend.model.TitleCategoryModel;
import org.hothtv.backend.model.TitleCategoryIdModel;
import org.hothtv.backend.repository.TitleCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleCategoryService {

    private final TitleCategoryRepository titleCategoryRepository;
    private final TitleRepository titleRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void addCategoryToTitle(Long titleId, Long categoryId) {
        // Validate both exist (prevents FK errors + nicer API errors)
        if (!titleRepository.existsById(titleId)) {
            throw new IllegalArgumentException("Title not found: " + titleId);
        }
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category not found: " + categoryId);
        }

        // Avoid duplicates
        if (titleCategoryRepository.existsByIdTitleIdAndIdCategoryId(titleId, categoryId)) {
            return; // idempotent
        }

        TitleCategoryModel link = new TitleCategoryModel(new TitleCategoryIdModel(titleId, categoryId));
        titleCategoryRepository.save(link);
    }

    @Transactional(readOnly = true)
    public List<TitleCategoryModel> listLinksForTitle(Long titleId) {
        return titleCategoryRepository.findAllByIdTitleId(titleId);
    }

    @Transactional
    public void removeCategoryFromTitle(Long titleId, Long categoryId) {
        titleCategoryRepository.deleteById(new TitleCategoryIdModel(titleId, categoryId));
    }
}
