package org.hothtv.backend.episodes.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.episodes.dto.CreateEpisodeRequest;
import org.hothtv.backend.episodes.model.Episode;
import org.hothtv.backend.episodes.service.EpisodeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @GetMapping("/seasons/{seasonId}/episodes")
    public List<Episode> list(@PathVariable Long seasonId) {
        return episodeService.listForSeason(seasonId);
    }

    @PostMapping("/seasons/{seasonId}/episodes")
    @ResponseStatus(HttpStatus.CREATED)
    public Episode create(@PathVariable Long seasonId, @RequestBody CreateEpisodeRequest req) {
        return episodeService.create(seasonId, req);
    }

    @DeleteMapping("/episodes/{episodeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long episodeId) {
        episodeService.delete(episodeId);
    }
}
