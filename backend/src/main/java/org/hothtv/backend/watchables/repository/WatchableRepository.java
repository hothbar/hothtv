package org.hothtv.backend.watchables.repository;

import org.hothtv.backend.watchables.model.Watchable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchableRepository extends JpaRepository<Watchable, Long> {}
