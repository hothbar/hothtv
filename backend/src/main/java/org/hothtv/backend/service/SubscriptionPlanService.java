package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.exceptions.NotFoundException;
import org.hothtv.backend.dto.CreatePlanRequestDto;
import org.hothtv.backend.dto.UpdatePlanRequestDto;
import org.hothtv.backend.model.SubscriptionPlanModel;
import org.hothtv.backend.repository.SubscriptionPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository planRepository;

    @Transactional(readOnly = true)
    public List<SubscriptionPlanModel> listPlans() {
        return planRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SubscriptionPlanModel getPlan(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan not found: " + id));
    }

    @Transactional
    public SubscriptionPlanModel createPlan(CreatePlanRequestDto req) {
        SubscriptionPlanModel plan = new SubscriptionPlanModel();
        plan.setName(req.name());
        plan.setPrice(req.price());
        plan.setDurationDays(req.durationDays());
        return planRepository.save(plan);
    }

    @Transactional
    public SubscriptionPlanModel updatePlan(Long id, UpdatePlanRequestDto req) {
        SubscriptionPlanModel plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan not found: " + id));
        if (req.name() != null) plan.setName(req.name());
        if (req.price() != null) plan.setPrice(req.price());
        if (req.durationDays() != null) plan.setDurationDays(req.durationDays());
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
