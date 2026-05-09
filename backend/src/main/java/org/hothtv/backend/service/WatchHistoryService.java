package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.exceptions.NotFoundException;
import org.hothtv.backend.repository.UserRepository;
import org.hothtv.backend.repository.WatchableRepository;
import org.hothtv.backend.dto.UpsertWatchHistoryRequestDto;
import org.hothtv.backend.model.WatchHistoryModel;
import org.hothtv.backend.repository.WatchHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;

    // IMPORTANT: these validations prevent the FK crash you got
    private final WatchableRepository watchableRepository;
    private final UserRepository userRepository; // adjust if your package differs

    @Transactional(readOnly = true)
    public List<WatchHistoryModel> listByUser(Long userId) {
        return watchHistoryRepository.findByUserId(userId);
    }

    @Transactional
    public WatchHistoryModel upsert(UpsertWatchHistoryRequestDto req) {
        if (!userRepository.existsById(req.userId())) {
            throw new NotFoundException("User not found: " + req.userId());
        }
        if (!watchableRepository.existsById(req.watchableId())) {
            throw new NotFoundException("Watchable not found: " + req.watchableId());
        }

        WatchHistoryModel wh = watchHistoryRepository
                .findByUserIdAndWatchableId(req.userId(), req.watchableId())
                .orElseGet(() -> {
                    WatchHistoryModel x = new WatchHistoryModel();
                    x.setUserId(req.userId());
                    x.setWatchableId(req.watchableId());
                    return x;
                });

        wh.setProgressSeconds(req.progressSeconds());
        wh.setCompleted(req.completed());
        return watchHistoryRepository.save(wh);
    }

    @Transactional
    public void delete(Long watchId) {
        if (!watchHistoryRepository.existsById(watchId)) {
            throw new NotFoundException("Watch history entry not found: " + watchId);
        }
        watchHistoryRepository.deleteById(watchId);
    }
}
