package org.hothtv.backend.repository;

import org.hothtv.backend.model.EpisodeWatchableModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EpisodeWatchableRepository extends JpaRepository<EpisodeWatchableModel, Long> {
    Optional<EpisodeWatchableModel> findByWatchableId(Long watchableId);
}
