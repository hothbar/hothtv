package org.hothtv.backend.people.repository;

import org.hothtv.backend.people.model.TitleDirector;
import org.hothtv.backend.people.model.TitleDirectorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleDirectorRepository extends JpaRepository<TitleDirector, TitleDirectorId> {}
