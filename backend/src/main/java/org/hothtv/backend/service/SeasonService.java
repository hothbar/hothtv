package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.dto.CreateSeasonRequestDto;
import org.hothtv.backend.model.SeasonModel;
import org.hothtv.backend.repository.SeasonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;

    @Transactional(readOnly = true)
    public List<SeasonModel> listForTitle(Long titleId) {
        return seasonRepository.findByTitleIdOrderBySeasonNumberAsc(titleId);
    }

    @Transactional
    public SeasonModel create(Long titleId, CreateSeasonRequestDto req) {
        if (req.seasonNumber() == null || req.seasonNumber() <= 0) {
            throw new IllegalArgumentException("seasonNumber must be > 0");
        }
        if (seasonRepository.existsByTitleIdAndSeasonNumber(titleId, req.seasonNumber())) {
            throw new IllegalArgumentException("Season already exists for this title");
        }

        SeasonModel s = new SeasonModel();
        s.setTitleId(titleId);
        s.setSeasonNumber(req.seasonNumber());
        return seasonRepository.save(s);
    }

    @Transactional
    public void delete(Long seasonId) {
        if (!seasonRepository.existsById(seasonId)) {
            throw new NotFoundException("Season not found: " + seasonId);
        }
        seasonRepository.deleteById(seasonId);
    }
}
