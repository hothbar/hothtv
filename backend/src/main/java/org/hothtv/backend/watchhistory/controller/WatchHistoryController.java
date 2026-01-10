package org.hothtv.backend.watchhistory.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.watchhistory.dto.UpsertWatchHistoryRequest;
import org.hothtv.backend.watchhistory.model.WatchHistory;
import org.hothtv.backend.watchhistory.service.WatchHistoryService;
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
    public WatchHistory upsert(@RequestBody UpsertWatchHistoryRequest req) {
        return watchHistoryService.upsert(req);
    }

    // GET /api/watchhistory/user/{userId}
    @GetMapping("/user/{userId}")
    public List<WatchHistory> list(@PathVariable Long userId) {
        return watchHistoryService.listByUser(userId);
    }
}
