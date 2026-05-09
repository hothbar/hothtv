package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreateSeasonRequestDto;
import org.hothtv.backend.dto.UpdateSeasonRequestDto;
import org.hothtv.backend.model.SeasonModel;
import org.hothtv.backend.service.SeasonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SeasonController {

    private final SeasonService seasonService;

    @GetMapping("/titles/{titleId}/seasons")
    public List<SeasonModel> list(@PathVariable Long titleId) {
        return seasonService.listForTitle(titleId);
    }

    @GetMapping("/seasons/{seasonId}")
    public SeasonModel get(@PathVariable Long seasonId) {
        return seasonService.get(seasonId);
    }

    @PostMapping("/titles/{titleId}/seasons")
    @ResponseStatus(HttpStatus.CREATED)
    public SeasonModel create(@PathVariable Long titleId, @RequestBody CreateSeasonRequestDto req) {
        return seasonService.create(titleId, req);
    }

    @PutMapping("/seasons/{seasonId}")
    public SeasonModel update(@PathVariable Long seasonId, @RequestBody UpdateSeasonRequestDto req) {
        return seasonService.update(seasonId, req);
    }

    @DeleteMapping("/seasons/{seasonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long seasonId) {
        seasonService.delete(seasonId);
    }
}
