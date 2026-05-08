package org.hothtv.backend.repository;

import org.hothtv.backend.model.SubscriptionModel;
import org.hothtv.backend.model.SubscriptionStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionModel, Long> {
    Optional<SubscriptionModel> findByUserIdAndStatus(Long userId, SubscriptionStatusModel status);
    List<SubscriptionModel> findByUserId(Long userId);
}
