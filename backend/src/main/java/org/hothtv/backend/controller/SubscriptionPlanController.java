package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreatePlanRequestDto;
import org.hothtv.backend.dto.UpdatePlanRequestDto;
import org.hothtv.backend.model.SubscriptionPlanModel;
import org.hothtv.backend.service.SubscriptionPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService planService;

    @GetMapping
    public List<SubscriptionPlanModel> list() {
        return planService.listPlans();
    }

    @GetMapping("/{id}")
    public SubscriptionPlanModel get(@PathVariable Long id) {
        return planService.getPlan(id);
    }

    // POST /api/plans
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionPlanModel create(@RequestBody CreatePlanRequestDto req) {
        return planService.createPlan(req);
    }

    @PutMapping("/{id}")
    public SubscriptionPlanModel update(@PathVariable Long id, @RequestBody UpdatePlanRequestDto req) {
        return planService.updatePlan(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        planService.deletePlan(id);
    }
}
