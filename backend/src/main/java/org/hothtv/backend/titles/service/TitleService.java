package org.hothtv.backend.titles.service;
import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.titles.dto.CreateTitleRequest;
import org.hothtv.backend.titles.model.Title;
import org.hothtv.backend.titles.model.TitleType;
import org.hothtv.backend.titles.repository.TitleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;

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
        // Implement after you add Category + TitleCategory tables/entities
        // For now, keep as a placeholder so endpoint exists.
        // Later: validate both exist, then insert into title_category.
        throw new UnsupportedOperationException("Not implemented yet. Add Category + TitleCategory first.");
    }
}