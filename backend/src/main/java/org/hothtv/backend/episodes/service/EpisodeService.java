package org.hothtv.backend.episodes.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.episodes.dto.CreateEpisodeRequest;
import org.hothtv.backend.episodes.model.Episode;
import org.hothtv.backend.episodes.repository.EpisodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    @Transactional(readOnly = true)
    public List<Episode> listForSeason(Long seasonId) {
        return episodeRepository.findBySeasonIdOrderByEpisodeNumberAsc(seasonId);
    }

    @Transactional
    public Episode create(Long seasonId, CreateEpisodeRequest req) {
        if (req.episodeNumber() == null || req.episodeNumber() <= 0) {
            throw new IllegalArgumentException("episodeNumber must be > 0");
        }
        if (req.durationMinutes() == null || req.durationMinutes() <= 0) {
            throw new IllegalArgumentException("durationMinutes must be > 0");
        }
        if (req.name() == null || req.name().isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (episodeRepository.existsBySeasonIdAndEpisodeNumber(seasonId, req.episodeNumber())) {
            throw new IllegalArgumentException("Episode number already exists in this season");
        }

        Episode e = new Episode();
        e.setSeasonId(seasonId);
        e.setEpisodeNumber(req.episodeNumber());
        e.setName(req.name().trim());
        e.setDurationMinutes(req.durationMinutes());
        return episodeRepository.save(e);
    }

    @Transactional
    public void delete(Long episodeId) {
        if (!episodeRepository.existsById(episodeId)) {
            throw new NotFoundException("Episode not found: " + episodeId);
        }
        episodeRepository.deleteById(episodeId);
    }
}
