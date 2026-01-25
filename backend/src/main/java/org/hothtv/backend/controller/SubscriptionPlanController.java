package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreatePlanRequestDto;
import org.hothtv.backend.model.SubscriptionPlanModel;
import org.hothtv.backend.service.SubscriptionPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
public class SubscriptionPlanController {
    // class member level
    // constructor level

    private final SubscriptionPlanService planService;

    // GET /api/plans
    @GetMapping
    public List<SubscriptionPlanModel> list() {
        return planService.listPlans();
    }

    // POST /api/plans
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionPlanModel create(@RequestBody CreatePlanRequestDto req) {
        return planService.createPlan(req);
    }

    // DELETE /api/plans/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        planService.deletePlan(id);
    }
}
