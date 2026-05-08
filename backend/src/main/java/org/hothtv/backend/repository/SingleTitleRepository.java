package org.hothtv.backend.repository;

import org.hothtv.backend.model.SingleTitleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleTitleRepository extends JpaRepository<SingleTitleModel, Long> {
}
