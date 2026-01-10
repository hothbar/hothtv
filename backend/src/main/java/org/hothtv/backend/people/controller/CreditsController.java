package org.hothtv.backend.people.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.people.dto.AddCastRequest;
import org.hothtv.backend.people.model.TitleCast;
import org.hothtv.backend.people.model.TitleDirector;
import org.hothtv.backend.people.service.CreditsService;
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
    public TitleCast addCast(@PathVariable Long titleId, @PathVariable Long personId,
                             @RequestBody(required = false) AddCastRequest req) {
        return creditsService.addCast(titleId, personId, req);
    }

    // POST /api/titles/{titleId}/directors/{personId}
    @PostMapping("/directors/{personId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TitleDirector addDirector(@PathVariable Long titleId, @PathVariable Long personId) {
        return creditsService.addDirector(titleId, personId);
    }
}
