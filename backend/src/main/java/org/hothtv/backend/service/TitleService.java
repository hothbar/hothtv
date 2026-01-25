package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.model.TitleCategoryModel;
import org.hothtv.backend.model.TitleCategoryIdModel;
import org.hothtv.backend.repository.CategoryRepository;
import org.hothtv.backend.repository.TitleCategoryRepository;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.dto.CreateTitleRequestDto;
import org.hothtv.backend.model.TitleModel;
import org.hothtv.backend.model.TitleTypeModel;
import org.hothtv.backend.repository.TitleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;
    private final CategoryRepository categoryRepository;
    private final TitleCategoryRepository titleCategoryRepository;

    @Transactional(readOnly = true)
    public List<TitleModel> listTitles(TitleTypeModel type) {
        return (type == null) ? titleRepository.findAll()
                : titleRepository.findByType(type);
    }

    @Transactional(readOnly = true)
    public TitleModel getTitle(Long id) {
        return titleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Title not found: " + id));
    }

    @Transactional
    public TitleModel createTitle(CreateTitleRequestDto req) {
        TitleModel title = new TitleModel();
        title.setType(req.type());
        title.setName(req.name());
        title.setReleaseDate(req.releaseDate());
        title.setDescription(req.description());
        return titleRepository.save(title);
    }

    @Transactional
    public void deleteTitle(Long id) {
        if (!titleRepository.existsById(id)) {
            throw new NotFoundException("Title not found: " + id);
        }
        titleRepository.deleteById(id);
    }

    @Transactional
    public void addCategoryToTitle(Long titleId, Long categoryId) {
        if (!titleRepository.existsById(titleId)) {
            throw new NotFoundException("Title not found: " + titleId);
        }
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category not found: " + categoryId);
        }

        TitleCategoryIdModel id = new TitleCategoryIdModel(titleId, categoryId);

        // idempotent: don't insert duplicate link
        if (titleCategoryRepository.existsById(id)) return;

        TitleCategoryModel link = new TitleCategoryModel();
        link.setId(id);

        titleCategoryRepository.save(link);
    }
}
