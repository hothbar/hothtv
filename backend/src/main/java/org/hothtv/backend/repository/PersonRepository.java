package org.hothtv.backend.repository;

import org.hothtv.backend.model.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonModel, Long> {}
