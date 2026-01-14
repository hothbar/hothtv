package org.hothtv.backend.seasons.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.seasons.dto.CreateSeasonRequest;
import org.hothtv.backend.seasons.model.Season;
import org.hothtv.backend.seasons.repository.SeasonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;

    @Transactional(readOnly = true)
    public List<Season> listForTitle(Long titleId) {
        return seasonRepository.findByTitleIdOrderBySeasonNumberAsc(titleId);
    }

    @Transactional
    public Season create(Long titleId, CreateSeasonRequest req) {
        if (req.seasonNumber() == null || req.seasonNumber() <= 0) {
            throw new IllegalArgumentException("seasonNumber must be > 0");
        }
        if (seasonRepository.existsByTitleIdAndSeasonNumber(titleId, req.seasonNumber())) {
            throw new IllegalArgumentException("Season already exists for this title");
        }

        Season s = new Season();
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
