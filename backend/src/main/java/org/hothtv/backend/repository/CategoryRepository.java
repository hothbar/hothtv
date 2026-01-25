package org.hothtv.backend.repository;

import org.hothtv.backend.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    boolean existsBySlug(String slug);
    Optional<CategoryModel> findBySlug(String slug);
}
