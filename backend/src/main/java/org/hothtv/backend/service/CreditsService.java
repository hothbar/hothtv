package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.exceptions.NotFoundException;
import org.hothtv.backend.dto.AddCastRequestDto;
import org.hothtv.backend.model.TitleCastModel;
import org.hothtv.backend.model.TitleDirectorModel;
import org.hothtv.backend.repository.PersonRepository;
import org.hothtv.backend.repository.TitleCastRepository;
import org.hothtv.backend.repository.TitleDirectorRepository;
import org.hothtv.backend.repository.TitleRepository;
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
    public TitleCastModel addCast(Long titleId, Long personId, AddCastRequestDto req) {
        if (!titleRepository.existsById(titleId)) throw new NotFoundException("Title not found: " + titleId);
        if (!personRepository.existsById(personId)) throw new NotFoundException("Person not found: " + personId);

        TitleCastModel cast = new TitleCastModel();
        cast.setTitleId(titleId);
        cast.setPersonId(personId);
        cast.setCharacterName(req == null ? null : req.characterName());
        cast.setBillingOrder(req == null ? null : req.billingOrder());

        return titleCastRepository.save(cast);
    }

    @Transactional
    public TitleDirectorModel addDirector(Long titleId, Long personId) {
        if (!titleRepository.existsById(titleId)) throw new NotFoundException("Title not found: " + titleId);
        if (!personRepository.existsById(personId)) throw new NotFoundException("Person not found: " + personId);

        TitleDirectorModel director = new TitleDirectorModel();
        director.setTitleId(titleId);
        director.setPersonId(personId);

        return titleDirectorRepository.save(director);
    }
}
