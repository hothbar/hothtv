package org.hothtv.backend.episodes.repository;

import org.hothtv.backend.episodes.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findBySeasonIdOrderByEpisodeNumberAsc(Long seasonId);
    boolean existsBySeasonIdAndEpisodeNumber(Long seasonId, Integer episodeNumber);
}
