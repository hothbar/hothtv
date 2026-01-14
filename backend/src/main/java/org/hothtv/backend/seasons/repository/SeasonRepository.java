package org.hothtv.backend.seasons.repository;

import org.hothtv.backend.seasons.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonRepository extends JpaRepository<Season, Long> {
    List<Season> findByTitleIdOrderBySeasonNumberAsc(Long titleId);
    boolean existsByTitleIdAndSeasonNumber(Long titleId, Integer seasonNumber);
}
