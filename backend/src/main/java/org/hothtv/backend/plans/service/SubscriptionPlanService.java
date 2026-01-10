package org.hothtv.backend.plans.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.plans.dto.CreatePlanRequest;
import org.hothtv.backend.plans.model.SubscriptionPlan;
import org.hothtv.backend.plans.repository.SubscriptionPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository planRepository;

    @Transactional(readOnly = true)
    public List<SubscriptionPlan> listPlans() {
        return planRepository.findAll();
    }

    @Transactional
    public SubscriptionPlan createPlan(CreatePlanRequest req) {
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setName(req.name());
        plan.setPrice(req.price());
        plan.setDurationDays(req.durationDays());
        return planRepository.save(plan);
    }

    @Transactional
    public void deletePlan(Long id) {
        if (!planRepository.existsById(id)) {
            throw new NotFoundException("Plan not found: " + id);
        }
        planRepository.deleteById(id);
    }
}
