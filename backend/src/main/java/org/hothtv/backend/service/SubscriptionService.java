package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreateSubscriptionRequestDto;
import org.hothtv.backend.exceptions.NotFoundException;
import org.hothtv.backend.model.SubscriptionModel;
import org.hothtv.backend.model.SubscriptionPlanModel;
import org.hothtv.backend.model.SubscriptionStatusModel;
import org.hothtv.backend.repository.SubscriptionPlanRepository;
import org.hothtv.backend.repository.SubscriptionRepository;
import org.hothtv.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionPlanRepository planRepository;
    private final UserRepository userRepository;

    @Transactional
    public SubscriptionModel subscribe(Long userId, CreateSubscriptionRequestDto req) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found: " + userId);
        }

        SubscriptionPlanModel plan = planRepository.findById(req.planId())
                .orElseThrow(() -> new NotFoundException("Plan not found: " + req.planId()));

        if (subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatusModel.ACTIVE).isPresent()) {
            throw new IllegalStateException("User already has an active subscription");
        }

        Instant now = Instant.now();
        SubscriptionModel sub = new SubscriptionModel();
        sub.setUserId(userId);
        sub.setPlanId(plan.getId());
        sub.setStartAt(now);
        sub.setEndAt(now.plus(plan.getDurationDays(), ChronoUnit.DAYS));
        sub.setStatus(SubscriptionStatusModel.ACTIVE);

        return subscriptionRepository.save(sub);
    }

    @Transactional(readOnly = true)
    public SubscriptionModel getCurrent(Long userId) {
        return subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatusModel.ACTIVE)
                .orElseThrow(() -> new NotFoundException("No active subscription for user: " + userId));
    }

    @Transactional
    public SubscriptionModel cancel(Long userId) {
        SubscriptionModel sub = getCurrent(userId);
        sub.setStatus(SubscriptionStatusModel.CANCELED);
        return subscriptionRepository.save(sub);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionModel> listByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found: " + userId);
        }
        return subscriptionRepository.findByUserId(userId);
    }
}
