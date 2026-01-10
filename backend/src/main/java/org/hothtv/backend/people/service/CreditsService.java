package org.hothtv.backend.people.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.people.dto.AddCastRequest;
import org.hothtv.backend.people.model.TitleCast;
import org.hothtv.backend.people.model.TitleDirector;
import org.hothtv.backend.people.repository.PersonRepository;
import org.hothtv.backend.people.repository.TitleCastRepository;
import org.hothtv.backend.people.repository.TitleDirectorRepository;
import org.hothtv.backend.titles.repository.TitleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditsService {

    private final TitleRepository titleRepository;
    private final PersonRepository personRepository;
    private final TitleCastRepository titleCastRepository;
    private final TitleDirectorRepository titleDirectorRepository;

    @Transactional
    public TitleCast addCast(Long titleId, Long personId, AddCastRequest req) {
        if (!titleRepository.existsById(titleId)) throw new NotFoundException("Title not found: " + titleId);
        if (!personRepository.existsById(personId)) throw new NotFoundException("Person not found: " + personId);

        TitleCast cast = new TitleCast();
        cast.setTitleId(titleId);
        cast.setPersonId(personId);
        cast.setCharacterName(req == null ? null : req.characterName());
        cast.setBillingOrder(req == null ? null : req.billingOrder());

        return titleCastRepository.save(cast);
    }

    @Transactional
    public TitleDirector addDirector(Long titleId, Long personId) {
        if (!titleRepository.existsById(titleId)) throw new NotFoundException("Title not found: " + titleId);
        if (!personRepository.existsById(personId)) throw new NotFoundException("Person not found: " + personId);

        TitleDirector director = new TitleDirector();
        director.setTitleId(titleId);
        director.setPersonId(personId);

        return titleDirectorRepository.save(director);
    }
}
