package org.hothtv.backend.watchhistory.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.watchhistory.dto.UpsertWatchHistoryRequest;
import org.hothtv.backend.watchhistory.model.WatchHistory;
import org.hothtv.backend.watchhistory.repository.WatchHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;

    @Transactional(readOnly = true)
    public List<WatchHistory> listByUser(Long userId) {
        return watchHistoryRepository.findByUserId(userId);
    }

    /**
     * Upsert by (userId, watchableId).
     * - If row exists: update progress/completed
     * - Else: create new row
     */
    @Transactional
    public WatchHistory upsert(UpsertWatchHistoryRequest req) {
        int progress = (req.progressSeconds() == null) ? 0 : Math.max(req.progressSeconds(), 0);
        boolean completed = (req.completed() != null) && req.completed();

        WatchHistory wh = watchHistoryRepository
                .findByUserIdAndWatchableId(req.userId(), req.watchableId())
                .orElseGet(() -> {
                    WatchHistory fresh = new WatchHistory();
                    fresh.setUserId(req.userId());
                    fresh.setWatchableId(req.watchableId());
                    return fresh;
                });

        wh.setProgressSeconds(progress);
        wh.setCompleted(completed);

        return watchHistoryRepository.save(wh);
    }
}
