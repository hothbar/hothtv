package org.hothtv.backend.repository;

import org.hothtv.backend.model.TitleCastModel;
import org.hothtv.backend.model.TitleCastIdModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleCastRepository extends JpaRepository<TitleCastModel, TitleCastIdModel> {}
