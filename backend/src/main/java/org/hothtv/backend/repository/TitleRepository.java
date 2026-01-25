package org.hothtv.backend.repository;

import org.hothtv.backend.model.TitleModel;
import org.hothtv.backend.model.TitleTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleRepository extends JpaRepository<TitleModel, Long> {
    public List<TitleModel> findByType(TitleTypeModel name);
}
