package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreateSubscriptionRequestDto;
import org.hothtv.backend.model.SubscriptionModel;
import org.hothtv.backend.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionModel subscribe(@PathVariable Long userId,
                                       @RequestBody CreateSubscriptionRequestDto req) {
        return subscriptionService.subscribe(userId, req);
    }

    @GetMapping("/current")
    public SubscriptionModel getCurrent(@PathVariable Long userId) {
        return subscriptionService.getCurrent(userId);
    }

    @PutMapping("/current/cancel")
    public SubscriptionModel cancel(@PathVariable Long userId) {
        return subscriptionService.cancel(userId);
    }

    @GetMapping
    public List<SubscriptionModel> list(@PathVariable Long userId) {
        return subscriptionService.listByUser(userId);
    }
}
