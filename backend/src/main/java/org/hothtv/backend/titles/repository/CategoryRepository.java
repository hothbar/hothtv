package org.hothtv.backend.titles.repository;

import org.hothtv.backend.categories.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
