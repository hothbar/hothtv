package org.hothtv.backend.people.repository;

import org.hothtv.backend.people.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
