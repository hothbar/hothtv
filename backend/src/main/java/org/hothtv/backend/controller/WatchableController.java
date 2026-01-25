package org.hothtv.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreateEpisodeWatchableRequestDto;
import org.hothtv.backend.dto.CreateSingleWatchableRequestDto;
import org.hothtv.backend.dto.WatchableDetailsResponseDto;
import org.hothtv.backend.model.WatchableModel;
import org.hothtv.backend.service.WatchableService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchables")
@RequiredArgsConstructor
public class WatchableController {

    private final WatchableService watchableService;

    @PostMapping("/single")
    @ResponseStatus(HttpStatus.CREATED)
    public WatchableModel createSingle(@Valid @RequestBody CreateSingleWatchableRequestDto req) {
        return watchableService.createSingleWatchable(req.singleTitleId());
    }

    @PostMapping("/episode")
    @ResponseStatus(HttpStatus.CREATED)
    public WatchableModel createEpisode(@Valid @RequestBody CreateEpisodeWatchableRequestDto req) {
        return watchableService.createEpisodeWatchable(req.episodeId());
    }

    @GetMapping("/{id}")
    public WatchableDetailsResponseDto getDetails(@PathVariable Long id) {
        return watchableService.getDetails(id);
    }
}
