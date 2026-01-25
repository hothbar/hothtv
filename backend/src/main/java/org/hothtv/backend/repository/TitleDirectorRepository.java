package org.hothtv.backend.repository;

import org.hothtv.backend.model.TitleDirectorModel;
import org.hothtv.backend.model.TitleDirectorIdModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleDirectorRepository extends JpaRepository<TitleDirectorModel, TitleDirectorIdModel> {}
