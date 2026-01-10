package org.hothtv.backend.titles.repository;

import org.hothtv.backend.titles.model.TitleCategory;
import org.hothtv.backend.titles.model.TitleCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleCategoryRepository extends JpaRepository<TitleCategory, TitleCategoryId> {
}
