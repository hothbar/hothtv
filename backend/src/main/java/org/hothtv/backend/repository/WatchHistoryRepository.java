package org.hothtv.backend.repository;

import org.hothtv.backend.model.WatchHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchHistoryRepository extends JpaRepository<WatchHistoryModel, Long> {
    List<WatchHistoryModel> findByUserId(Long userId);
    Optional<WatchHistoryModel> findByUserIdAndWatchableId(Long userId, Long watchableId);
}
