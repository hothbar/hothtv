package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.AddCastRequestDto;
import org.hothtv.backend.dto.UpdateCastRequestDto;
import org.hothtv.backend.model.TitleCastModel;
import org.hothtv.backend.model.TitleDirectorModel;
import org.hothtv.backend.service.CreditsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/titles/{titleId}")
public class CreditsController {

    private final CreditsService creditsService;

    @GetMapping("/cast")
    public List<TitleCastModel> listCast(@PathVariable Long titleId) {
        return creditsService.listCast(titleId);
    }

    @PostMapping("/cast/{personId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TitleCastModel addCast(@PathVariable Long titleId, @PathVariable Long personId,
                                  @RequestBody(required = false) AddCastRequestDto req) {
        return creditsService.addCast(titleId, personId, req);
    }

    @PutMapping("/cast/{personId}")
    public TitleCastModel updateCast(@PathVariable Long titleId, @PathVariable Long personId,
                                     @RequestBody UpdateCastRequestDto req) {
        return creditsService.updateCast(titleId, personId, req);
    }

    @DeleteMapping("/cast/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCast(@PathVariable Long titleId, @PathVariable Long personId) {
        creditsService.removeCast(titleId, personId);
    }

    @GetMapping("/directors")
    public List<TitleDirectorModel> listDirectors(@PathVariable Long titleId) {
        return creditsService.listDirectors(titleId);
    }

    @PostMapping("/directors/{personId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TitleDirectorModel addDirector(@PathVariable Long titleId, @PathVariable Long personId) {
        return creditsService.addDirector(titleId, personId);
    }

    @DeleteMapping("/directors/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDirector(@PathVariable Long titleId, @PathVariable Long personId) {
        creditsService.removeDirector(titleId, personId);
    }
}
