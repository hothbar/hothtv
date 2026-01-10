package org.hothtv.backend.watchhistory.repository;

import org.hothtv.backend.watchhistory.model.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {
    List<WatchHistory> findByUserId(Long userId);
    Optional<WatchHistory> findByUserIdAndWatchableId(Long userId, Long watchableId);
}
