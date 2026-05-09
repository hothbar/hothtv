package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreatePersonRequestDto;
import org.hothtv.backend.dto.UpdatePersonRequestDto;
import org.hothtv.backend.exceptions.NotFoundException;
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

    @Transactional(readOnly = true)
    public PersonModel get(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found: " + id));
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

    @Transactional
    public PersonModel update(Long id, UpdatePersonRequestDto req) {
        PersonModel p = get(id);
        if (req.firstName() != null) p.setFirstName(req.firstName());
        if (req.lastName() != null) p.setLastName(req.lastName());
        if (req.dateOfBirth() != null) p.setDateOfBirth(req.dateOfBirth());
        if (req.dateOfDeath() != null) p.setDateOfDeath(req.dateOfDeath());
        return personRepository.save(p);
    }

    @Transactional
    public void delete(Long id) {
        if (!personRepository.existsById(id)) throw new NotFoundException("Person not found: " + id);
        personRepository.deleteById(id);
    }
}
