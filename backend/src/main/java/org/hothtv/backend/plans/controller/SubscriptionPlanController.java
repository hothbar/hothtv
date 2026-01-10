package org.hothtv.backend.plans.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.plans.dto.CreatePlanRequest;
import org.hothtv.backend.plans.model.SubscriptionPlan;
import org.hothtv.backend.plans.service.SubscriptionPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService planService;

    // GET /api/plans
    @GetMapping
    public List<SubscriptionPlan> list() {
        return planService.listPlans();
    }

    // POST /api/plans
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionPlan create(@RequestBody CreatePlanRequest req) {
        return planService.createPlan(req);
    }

    // DELETE /api/plans/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        planService.deletePlan(id);
    }
}
