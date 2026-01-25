package org.hothtv.backend.repository;

import org.hothtv.backend.model.WatchableModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchableRepository extends JpaRepository<WatchableModel, Long> {}
