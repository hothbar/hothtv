package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.UpsertWatchHistoryRequestDto;
import org.hothtv.backend.model.WatchHistoryModel;
import org.hothtv.backend.service.WatchHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/watchhistory")
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    // POST /api/watchhistory  (upsert)
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public WatchHistoryModel upsert(@RequestBody UpsertWatchHistoryRequestDto req) {
        return watchHistoryService.upsert(req);
    }

    // GET /api/watchhistory/user/{userId}
    @GetMapping("/user/{userId}")
    public List<WatchHistoryModel> list(@PathVariable Long userId) {
        return watchHistoryService.listByUser(userId);
    }
}
