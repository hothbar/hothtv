package org.hothtv.backend.repository;

import org.hothtv.backend.model.SubscriptionPlanModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlanModel, Long> {
    boolean existsByName(String name);
}
