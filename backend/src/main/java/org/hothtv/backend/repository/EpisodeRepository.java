package org.hothtv.backend.repository;

import org.hothtv.backend.model.EpisodeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<EpisodeModel, Long> {
    List<EpisodeModel> findBySeasonIdOrderByEpisodeNumberAsc(Long seasonId);
    boolean existsBySeasonIdAndEpisodeNumber(Long seasonId, Integer episodeNumber);
}
