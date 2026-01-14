package org.hothtv.backend.titles.service;
import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.titles.dto.CreateTitleRequest;
import org.hothtv.backend.titles.model.Title;
import org.hothtv.backend.categories.model.TitleCategory;
import org.hothtv.backend.titles.model.TitleType;
import org.hothtv.backend.titles.repository.CategoryRepository;
import org.hothtv.backend.titles.repository.TitleCategoryRepository;
import org.hothtv.backend.titles.repository.TitleRepository;
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
    public List<Title> listTitles(TitleType type) {
        if (type == null) return titleRepository.findAll();
        return titleRepository.findByType(type);
    }

    @Transactional(readOnly = true)
    public Title getTitle(Long id) {
        return titleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Title not found: " + id));
    }

    @Transactional
    public Title createTitle(CreateTitleRequest req) {
        Title title = new Title();
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
        // confirm both exist (nice 404 behavior)
        if (!titleRepository.existsById(titleId)) {
            throw new NotFoundException("Title not found: " + titleId);
        }
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category not found: " + categoryId);
        }

        // idempotent: if link exists, do nothing
        TitleCategory link = new TitleCategory(titleId, categoryId);
        titleCategoryRepository.save(link);
    }

}