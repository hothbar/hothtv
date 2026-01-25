package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreatePersonRequestDto;
import org.hothtv.backend.model.PersonModel;
import org.hothtv.backend.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    public List<PersonModel> list() {
        return personRepository.findAll();
    }

    @Transactional
    public PersonModel create(CreatePersonRequestDto req) {
        PersonModel p = new PersonModel();
        p.setFirstName(req.firstName());
        p.setLastName(req.lastName());
        p.setDateOfBirth(req.dateOfBirth());
        p.setDateOfDeath(req.dateOfDeath());
        return personRepository.save(p);
    }
}
