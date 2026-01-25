package org.hothtv.backend.repository;

import org.hothtv.backend.model.TitleCategoryModel;
import org.hothtv.backend.model.TitleCategoryIdModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleCategoryRepository extends JpaRepository<TitleCategoryModel, TitleCategoryIdModel> {

    List<TitleCategoryModel> findAllByIdTitleId(Long titleId);

    boolean existsByIdTitleIdAndIdCategoryId(Long titleId, Long categoryId);

    void deleteAllByIdTitleId(Long titleId);
}
