package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreatePersonRequestDto;
import org.hothtv.backend.model.PersonModel;
import org.hothtv.backend.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/people")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<PersonModel> list() {
        return personService.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonModel create(@RequestBody CreatePersonRequestDto req) {
        return personService.create(req);
    }
}
