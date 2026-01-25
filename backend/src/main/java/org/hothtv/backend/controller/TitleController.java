package org.hothtv.backend.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreateTitleRequestDto;
import org.hothtv.backend.model.TitleModel;
import org.hothtv.backend.model.TitleTypeModel;
import org.hothtv.backend.service.TitleService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/titles")
public class TitleController {

    private final TitleService titleService;

    // GET /api/titles?type=SINGLE|SERIES
    @GetMapping
    public List<TitleModel> list(@RequestParam(required = false) TitleTypeModel type) {
        return titleService.listTitles(type);
    }

    // GET /api/titles/{id}
    @GetMapping("/{id}")
    public TitleModel get(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return titleService.getTitle(id);
    }

    // POST /api/titles
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TitleModel create(@RequestBody CreateTitleRequestDto req) {
        return titleService.createTitle(req);
    }

    // DELETE /api/titles/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        titleService.deleteTitle(id);
    }

    // POST /api/titles/{id}/categories/{categoryId}
    @PostMapping("/{id}/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCategory(@PathVariable Long id, @PathVariable Long categoryId) {
        titleService.addCategoryToTitle(id, categoryId);
    }
}