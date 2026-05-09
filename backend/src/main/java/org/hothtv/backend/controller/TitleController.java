package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreateTitleRequestDto;
import org.hothtv.backend.dto.UpdateTitleRequestDto;
import org.hothtv.backend.model.TitleModel;
import org.hothtv.backend.model.TitleTypeModel;
import org.hothtv.backend.service.TitleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/titles")
public class TitleController {

    private final TitleService titleService;

    @GetMapping
    public List<TitleModel> list(@RequestParam(required = false) TitleTypeModel type) {
        return titleService.listTitles(type);
    }

    @GetMapping("/{id}")
    public TitleModel get(@PathVariable Long id) {
        return titleService.getTitle(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TitleModel create(@RequestBody CreateTitleRequestDto req) {
        return titleService.createTitle(req);
    }

    @PutMapping("/{id}")
    public TitleModel update(@PathVariable Long id, @RequestBody UpdateTitleRequestDto req) {
        return titleService.updateTitle(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        titleService.deleteTitle(id);
    }
}