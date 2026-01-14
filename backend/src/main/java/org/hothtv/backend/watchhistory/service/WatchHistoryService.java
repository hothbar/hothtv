package org.hothtv.backend.watchhistory.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.watchables.repository.WatchableRepository;
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

    // IMPORTANT: these validations prevent the FK crash you got
    private final WatchableRepository watchableRepository;
    private final org.hothtv.backend.users.repository.UserRepository userRepository; // adjust if your package differs

    @Transactional(readOnly = true)
    public List<WatchHistory> listByUser(Long userId) {
        return watchHistoryRepository.findByUserId(userId);
    }

    @Transactional
    public WatchHistory upsert(UpsertWatchHistoryRequest req) {
        if (!userRepository.existsById(req.userId())) {
            throw new NotFoundException("User not found: " + req.userId());
        }
        if (!watchableRepository.existsById(req.watchableId())) {
            throw new NotFoundException("Watchable not found: " + req.watchableId());
        }

        WatchHistory wh = watchHistoryRepository
                .findByUserIdAndWatchableId(req.userId(), req.watchableId())
                .orElseGet(() -> {
                    WatchHistory x = new WatchHistory();
                    x.setUserId(req.userId());
                    x.setWatchableId(req.watchableId());
                    return x;
                });

        wh.setProgressSeconds(req.progressSeconds());
        wh.setCompleted(req.completed());
        return watchHistoryRepository.save(wh);
    }
}
