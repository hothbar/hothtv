package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreateEpisodeRequestDto;
import org.hothtv.backend.dto.UpdateEpisodeRequestDto;
import org.hothtv.backend.model.EpisodeModel;
import org.hothtv.backend.service.EpisodeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @GetMapping("/seasons/{seasonId}/episodes")
    public List<EpisodeModel> list(@PathVariable Long seasonId) {
        return episodeService.listForSeason(seasonId);
    }

    @GetMapping("/episodes/{episodeId}")
    public EpisodeModel get(@PathVariable Long episodeId) {
        return episodeService.get(episodeId);
    }

    @PostMapping("/seasons/{seasonId}/episodes")
    @ResponseStatus(HttpStatus.CREATED)
    public EpisodeModel create(@PathVariable Long seasonId, @RequestBody CreateEpisodeRequestDto req) {
        return episodeService.create(seasonId, req);
    }

    @PutMapping("/episodes/{episodeId}")
    public EpisodeModel update(@PathVariable Long episodeId, @RequestBody UpdateEpisodeRequestDto req) {
        return episodeService.update(episodeId, req);
    }

    @DeleteMapping("/episodes/{episodeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long episodeId) {
        episodeService.delete(episodeId);
    }
}
