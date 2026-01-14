package org.hothtv.backend.watchables.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hothtv.backend.watchables.dto.*;
import org.hothtv.backend.watchables.model.Watchable;
import org.hothtv.backend.watchables.service.WatchableService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchables")
@RequiredArgsConstructor
public class WatchableController {

    private final WatchableService watchableService;

    @PostMapping("/single")
    @ResponseStatus(HttpStatus.CREATED)
    public Watchable createSingle(@Valid @RequestBody CreateSingleWatchableRequest req) {
        return watchableService.createSingleWatchable(req.singleTitleId());
    }

    @PostMapping("/episode")
    @ResponseStatus(HttpStatus.CREATED)
    public Watchable createEpisode(@Valid @RequestBody CreateEpisodeWatchableRequest req) {
        return watchableService.createEpisodeWatchable(req.episodeId());
    }

    @GetMapping("/{id}")
    public WatchableDetailsResponse getDetails(@PathVariable Long id) {
        return watchableService.getDetails(id);
    }
}
