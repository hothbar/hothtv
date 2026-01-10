package org.hothtv.backend.people.repository;

import org.hothtv.backend.people.model.TitleCast;
import org.hothtv.backend.people.model.TitleCastId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleCastRepository extends JpaRepository<TitleCast, TitleCastId> {}
