package org.hothtv.backend.repository;

import org.hothtv.backend.model.SingleWatchableModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SingleWatchableRepository extends JpaRepository<SingleWatchableModel, Long> {
    Optional<SingleWatchableModel> findByWatchableId(Long watchableId);
}
