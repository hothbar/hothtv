package org.hothtv.backend.seasons.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.seasons.dto.CreateSeasonRequest;
import org.hothtv.backend.seasons.model.Season;
import org.hothtv.backend.seasons.service.SeasonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SeasonController {

    private final SeasonService seasonService;

    @GetMapping("/titles/{titleId}/seasons")
    public List<Season> list(@PathVariable Long titleId) {
        return seasonService.listForTitle(titleId);
    }

    @PostMapping("/titles/{titleId}/seasons")
    @ResponseStatus(HttpStatus.CREATED)
    public Season create(@PathVariable Long titleId, @RequestBody CreateSeasonRequest req) {
        return seasonService.create(titleId, req);
    }

    @DeleteMapping("/seasons/{seasonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long seasonId) {
        seasonService.delete(seasonId);
    }
}
