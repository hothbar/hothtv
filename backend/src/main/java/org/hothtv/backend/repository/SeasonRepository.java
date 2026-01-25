package org.hothtv.backend.repository;

import org.hothtv.backend.model.SeasonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonRepository extends JpaRepository<SeasonModel, Long> {
    List<SeasonModel> findByTitleIdOrderBySeasonNumberAsc(Long titleId);
    boolean existsByTitleIdAndSeasonNumber(Long titleId, Integer seasonNumber);
}
