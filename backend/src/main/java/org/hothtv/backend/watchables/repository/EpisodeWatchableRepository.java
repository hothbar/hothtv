package org.hothtv.backend.watchables.repository;

import org.hothtv.backend.watchables.model.EpisodeWatchable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EpisodeWatchableRepository extends JpaRepository<EpisodeWatchable, Long> {
    Optional<EpisodeWatchable> findByWatchableId(Long watchableId);
}
