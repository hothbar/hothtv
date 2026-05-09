package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.exceptions.NotFoundException;
import org.hothtv.backend.dto.CreateSeasonRequestDto;
import org.hothtv.backend.dto.UpdateSeasonRequestDto;
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

    @Transactional(readOnly = true)
    public SeasonModel get(Long seasonId) {
        return seasonRepository.findById(seasonId)
                .orElseThrow(() -> new NotFoundException("Season not found: " + seasonId));
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
    public SeasonModel update(Long seasonId, UpdateSeasonRequestDto req) {
        SeasonModel s = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new NotFoundException("Season not found: " + seasonId));
        if (req.seasonNumber() != null && !req.seasonNumber().equals(s.getSeasonNumber())) {
            if (req.seasonNumber() <= 0) throw new IllegalArgumentException("seasonNumber must be > 0");
            if (seasonRepository.existsByTitleIdAndSeasonNumber(s.getTitleId(), req.seasonNumber())) {
                throw new IllegalArgumentException("Season number already exists for this title");
            }
            s.setSeasonNumber(req.seasonNumber());
        }
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
