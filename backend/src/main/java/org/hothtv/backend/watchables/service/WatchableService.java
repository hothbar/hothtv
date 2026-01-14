package org.hothtv.backend.watchables.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.watchables.dto.WatchableDetailsResponse;
import org.hothtv.backend.watchables.model.*;
import org.hothtv.backend.watchables.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WatchableService {

    private final WatchableRepository watchableRepository;
    private final SingleWatchableRepository singleWatchableRepository;
    private final EpisodeWatchableRepository episodeWatchableRepository;

    // Optional (recommended): validate referenced IDs exist
    // private final SingleTitleRepository singleTitleRepository;
    // private final EpisodeRepository episodeRepository;

    @Transactional
    public Watchable createSingleWatchable(Long singleTitleId) {
        // if (!singleTitleRepository.existsById(singleTitleId)) throw new NotFoundException("SingleTitle not found: " + singleTitleId);

        Watchable w = new Watchable();
        w.setKind(WatchableKind.SINGLE);
        w = watchableRepository.save(w);

        SingleWatchable sw = new SingleWatchable();
        sw.setWatchable(w);
        sw.setSingleTitleId(singleTitleId);
        singleWatchableRepository.save(sw);

        return w;
    }

    @Transactional
    public Watchable createEpisodeWatchable(Long episodeId) {
        // if (!episodeRepository.existsById(episodeId)) throw new NotFoundException("Episode not found: " + episodeId);

        Watchable w = new Watchable();
        w.setKind(WatchableKind.EPISODE);
        w = watchableRepository.save(w);

        EpisodeWatchable ew = new EpisodeWatchable();
        ew.setWatchable(w);
        ew.setEpisodeId(episodeId);
        episodeWatchableRepository.save(ew);

        return w;
    }

    @Transactional(readOnly = true)
    public WatchableDetailsResponse getDetails(Long watchableId) {
        Watchable w = watchableRepository.findById(watchableId)
                .orElseThrow(() -> new NotFoundException("Watchable not found: " + watchableId));

        Long singleTitleId = null;
        Long episodeId = null;

        if (w.getKind() == WatchableKind.SINGLE) {
            singleTitleId = singleWatchableRepository.findById(watchableId)
                    .orElseThrow(() -> new NotFoundException("single_watchable missing for watchable: " + watchableId))
                    .getSingleTitleId();
        } else {
            episodeId = episodeWatchableRepository.findById(watchableId)
                    .orElseThrow(() -> new NotFoundException("episode_watchable missing for watchable: " + watchableId))
                    .getEpisodeId();
        }

        return new WatchableDetailsResponse(w.getId(), w.getKind(), singleTitleId, episodeId);
    }
}
