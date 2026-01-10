package org.hothtv.backend.people.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.people.dto.CreatePersonRequest;
import org.hothtv.backend.people.model.Person;
import org.hothtv.backend.people.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/people")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<Person> list() {
        return personService.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody CreatePersonRequest req) {
        return personService.create(req);
    }
}
