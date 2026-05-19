package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.exceptions.NotFoundException;
import org.hothtv.backend.model.EpisodeWatchableModel;
import org.hothtv.backend.model.SingleWatchableModel;
import org.hothtv.backend.model.WatchableKindModel;
import org.hothtv.backend.model.WatchableModel;
import org.hothtv.backend.repository.EpisodeRepository;
import org.hothtv.backend.repository.EpisodeWatchableRepository;
import org.hothtv.backend.repository.SingleTitleRepository;
import org.hothtv.backend.repository.SingleWatchableRepository;
import org.hothtv.backend.repository.WatchableRepository;
import org.hothtv.backend.dto.WatchableDetailsResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchableService {

    private final WatchableRepository watchableRepository;
    private final SingleWatchableRepository singleWatchableRepository;
    private final EpisodeWatchableRepository episodeWatchableRepository;
    private final SingleTitleRepository singleTitleRepository;
    private final EpisodeRepository episodeRepository;

    @Transactional(readOnly = true)
    public List<WatchableModel> listAll() {
        return watchableRepository.findAll();
    }

    @Transactional
    public WatchableModel createSingleWatchable(Long singleTitleId) {
        if (!singleTitleRepository.existsById(singleTitleId)) throw new NotFoundException("SingleTitle not found: " + singleTitleId);

        WatchableModel w = new WatchableModel();
        w.setKind(WatchableKindModel.SINGLE);
        w = watchableRepository.save(w);

        SingleWatchableModel sw = new SingleWatchableModel();
        sw.setWatchable(w);
        sw.setSingleTitleId(singleTitleId);
        singleWatchableRepository.save(sw);

        return w;
    }

    @Transactional
    public WatchableModel createEpisodeWatchable(Long episodeId) {
        if (!episodeRepository.existsById(episodeId)) throw new NotFoundException("Episode not found: " + episodeId);

        WatchableModel w = new WatchableModel();
        w.setKind(WatchableKindModel.EPISODE);
        w = watchableRepository.save(w);

        EpisodeWatchableModel ew = new EpisodeWatchableModel();
        ew.setWatchable(w);
        ew.setEpisodeId(episodeId);
        episodeWatchableRepository.save(ew);

        return w;
    }

    @Transactional
    public void delete(Long watchableId) {
        if (!watchableRepository.existsById(watchableId)) {
            throw new NotFoundException("Watchable not found: " + watchableId);
        }
        watchableRepository.deleteById(watchableId);
    }

    @Transactional(readOnly = true)
    public WatchableDetailsResponseDto getDetails(Long watchableId) {
        WatchableModel w = watchableRepository.findById(watchableId)
                .orElseThrow(() -> new NotFoundException("Watchable not found: " + watchableId));

        Long singleTitleId = null;
        Long episodeId = null;

        if (w.getKind() == WatchableKindModel.SINGLE) {
            singleTitleId = singleWatchableRepository.findById(watchableId)
                    .orElseThrow(() -> new NotFoundException("single_watchable missing for watchable: " + watchableId))
                    .getSingleTitleId();
        } else {
            episodeId = episodeWatchableRepository.findById(watchableId)
                    .orElseThrow(() -> new NotFoundException("episode_watchable missing for watchable: " + watchableId))
                    .getEpisodeId();
        }

        return new WatchableDetailsResponseDto(w.getId(), w.getKind(), singleTitleId, episodeId);
    }
}
