package org.hothtv.backend.watchables.repository;

import org.hothtv.backend.watchables.model.SingleWatchable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SingleWatchableRepository extends JpaRepository<SingleWatchable, Long> {
    Optional<SingleWatchable> findByWatchableId(Long watchableId);
}
