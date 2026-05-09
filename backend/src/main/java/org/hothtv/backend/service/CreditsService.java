package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.exceptions.NotFoundException;
import org.hothtv.backend.dto.AddCastRequestDto;
import org.hothtv.backend.dto.UpdateCastRequestDto;
import org.hothtv.backend.model.TitleCastIdModel;
import org.hothtv.backend.model.TitleCastModel;
import org.hothtv.backend.model.TitleDirectorIdModel;
import org.hothtv.backend.model.TitleDirectorModel;
import org.hothtv.backend.repository.PersonRepository;
import org.hothtv.backend.repository.TitleCastRepository;
import org.hothtv.backend.repository.TitleDirectorRepository;
import org.hothtv.backend.repository.TitleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditsService {

    private final TitleRepository titleRepository;
    private final PersonRepository personRepository;
    private final TitleCastRepository titleCastRepository;
    private final TitleDirectorRepository titleDirectorRepository;

    @Transactional(readOnly = true)
    public List<TitleCastModel> listCast(Long titleId) {
        if (!titleRepository.existsById(titleId)) throw new NotFoundException("Title not found: " + titleId);
        return titleCastRepository.findByTitleId(titleId);
    }

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
    public TitleCastModel updateCast(Long titleId, Long personId, UpdateCastRequestDto req) {
        TitleCastIdModel id = new TitleCastIdModel(titleId, personId);
        TitleCastModel cast = titleCastRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cast entry not found"));
        if (req.characterName() != null) cast.setCharacterName(req.characterName());
        if (req.billingOrder() != null) cast.setBillingOrder(req.billingOrder());
        return titleCastRepository.save(cast);
    }

    @Transactional
    public void removeCast(Long titleId, Long personId) {
        TitleCastIdModel id = new TitleCastIdModel(titleId, personId);
        if (!titleCastRepository.existsById(id)) throw new NotFoundException("Cast entry not found");
        titleCastRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TitleDirectorModel> listDirectors(Long titleId) {
        if (!titleRepository.existsById(titleId)) throw new NotFoundException("Title not found: " + titleId);
        return titleDirectorRepository.findByTitleId(titleId);
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

    @Transactional
    public void removeDirector(Long titleId, Long personId) {
        TitleDirectorIdModel id = new TitleDirectorIdModel(titleId, personId);
        if (!titleDirectorRepository.existsById(id)) throw new NotFoundException("Director entry not found");
        titleDirectorRepository.deleteById(id);
    }
}
