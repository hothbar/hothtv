package org.hothtv.backend.repository;

import org.hothtv.backend.model.TitleCastModel;
import org.hothtv.backend.model.TitleCastIdModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleCastRepository extends JpaRepository<TitleCastModel, TitleCastIdModel> {
    List<TitleCastModel> findByTitleId(Long titleId);
}
