package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.model.SingleTitleModel;
import org.hothtv.backend.repository.SingleTitleRepository;
import org.hothtv.backend.exceptions.NotFoundException;
import org.hothtv.backend.dto.CreateTitleRequestDto;
import org.hothtv.backend.dto.UpdateTitleRequestDto;
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
    private final SingleTitleRepository singleTitleRepository;

    @Transactional(readOnly = true)
    public List<TitleModel> listTitles(TitleTypeModel type) {
        return (type == null) ? titleRepository.findAll()
                : titleRepository.findByType(type);
    }

    @Transactional(readOnly = true)
    public TitleModel getTitle(Long id) {
        TitleModel title = titleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Title not found: " + id));
        if (title.getType() == TitleTypeModel.SINGLE) {
            singleTitleRepository.findById(id)
                    .ifPresent(st -> title.setDurationMinutes(st.getDurationMinutes()));
        }
        return title;
    }

    @Transactional
    public TitleModel updateTitle(Long id, UpdateTitleRequestDto req) {
        TitleModel title = titleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Title not found: " + id));
        if (req.name() != null) title.setName(req.name());
        if (req.releaseDate() != null) title.setReleaseDate(req.releaseDate());
        if (req.description() != null) title.setDescription(req.description());
        return titleRepository.save(title);
    }

    @Transactional
    public TitleModel createTitle(CreateTitleRequestDto req) {
        if (req.type() == TitleTypeModel.SINGLE) {
            if (req.durationMinutes() == null || req.durationMinutes() <= 0) {
                throw new IllegalArgumentException("durationMinutes is required for SINGLE titles and must be > 0");
            }
        }

        TitleModel title = new TitleModel();
        title.setType(req.type());
        title.setName(req.name());
        title.setReleaseDate(req.releaseDate());
        title.setDescription(req.description());
        title = titleRepository.save(title);

        if (req.type() == TitleTypeModel.SINGLE) {
            SingleTitleModel st = new SingleTitleModel();
            st.setTitle(title);
            st.setDurationMinutes(req.durationMinutes());
            singleTitleRepository.save(st);
        }

        return title;
    }

    @Transactional
    public void deleteTitle(Long id) {
        if (!titleRepository.existsById(id)) {
            throw new NotFoundException("Title not found: " + id);
        }
        titleRepository.deleteById(id);
    }

}
