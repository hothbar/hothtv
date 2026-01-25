package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.AddCastRequestDto;
import org.hothtv.backend.model.TitleCastModel;
import org.hothtv.backend.model.TitleDirectorModel;
import org.hothtv.backend.service.CreditsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/titles/{titleId}")
public class CreditsController {

    private final CreditsService creditsService;

    // POST /api/titles/{titleId}/cast/{personId}
    @PostMapping("/cast/{personId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TitleCastModel addCast(@PathVariable Long titleId, @PathVariable Long personId,
                                  @RequestBody(required = false) AddCastRequestDto req) {
        return creditsService.addCast(titleId, personId, req);
    }

    // POST /api/titles/{titleId}/directors/{personId}
    @PostMapping("/directors/{personId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TitleDirectorModel addDirector(@PathVariable Long titleId, @PathVariable Long personId) {
        return creditsService.addDirector(titleId, personId);
    }
}
