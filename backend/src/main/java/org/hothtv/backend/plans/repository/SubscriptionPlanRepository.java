package org.hothtv.backend.plans.repository;

import org.hothtv.backend.plans.model.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
    boolean existsByName(String name);
}
