package org.hothtv.backend.titles.repository;

import org.hothtv.backend.titles.model.Title;
import org.hothtv.backend.titles.model.TitleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, Long> {
    public List<Title> findByType(TitleType name);
}
