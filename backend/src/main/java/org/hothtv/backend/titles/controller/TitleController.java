package org.hothtv.backend.titles.controller;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hothtv.backend.titles.dto.CreateTitleRequest;
import org.hothtv.backend.titles.model.Title;
import org.hothtv.backend.titles.model.TitleType;
import org.hothtv.backend.titles.service.TitleService;
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
    public List<Title> list(@RequestParam(required = false) TitleType type) {
        return titleService.listTitles(type);
    }

    // GET /api/titles/{id}
    @GetMapping("/{id}")
    public Title get(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return titleService.getTitle(id);
    }

    // POST /api/titles
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Title create(@RequestBody CreateTitleRequest req) {
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