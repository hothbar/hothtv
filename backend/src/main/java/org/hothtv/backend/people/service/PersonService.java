package org.hothtv.backend.people.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.people.dto.CreatePersonRequest;
import org.hothtv.backend.people.model.Person;
import org.hothtv.backend.people.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    public List<Person> list() {
        return personRepository.findAll();
    }

    @Transactional
    public Person create(CreatePersonRequest req) {
        Person p = new Person();
        p.setFirstName(req.firstName());
        p.setLastName(req.lastName());
        p.setDateOfBirth(req.dateOfBirth());
        p.setDateOfDeath(req.dateOfDeath());
        return personRepository.save(p);
    }
}
