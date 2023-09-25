package com.monaschinas.readr.platform.publishing.service;

import com.monaschinas.readr.platform.publishing.domain.model.Genre;
import com.monaschinas.readr.platform.publishing.domain.persistence.GenreRepository;
import com.monaschinas.readr.platform.publishing.domain.service.GenreService;
import com.monaschinas.readr.platform.shared.exception.ResourceNotFoundException;
import com.monaschinas.readr.platform.shared.exception.ResourceValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GenreServiceImpl implements GenreService {
    private static final String ENTITY = "Genre";

    private final GenreRepository genreRepository;

    private final Validator validator;

    public GenreServiceImpl(GenreRepository genreRepository, Validator validator) {
        this.genreRepository = genreRepository;
        this.validator = validator;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Page<Genre> getAll(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }

    @Override
    public Genre getById(Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY,genreId));
    }

    @Override
    public Genre create(Genre genre) {
        Set<ConstraintViolation<Genre>> violations = validator.validate(genre);

        if(!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }
        if(genreRepository.findByName(genre.getName()).isPresent()) {
            throw new ResourceValidationException(ENTITY, "A genre with the same name already exists");
        }

        return genreRepository.save(genre);
    }

    @Override
    public Genre update(Long genreId, Genre genre) {
        Set<ConstraintViolation<Genre>> violations = validator.validate(genre);

        if(!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }
        Optional<Genre> genreWithName = genreRepository.findByName(genre.getName());
        if(genreWithName.isPresent() && !genreWithName.get().getId().equals(genre.getId())) {
            throw new ResourceValidationException(ENTITY, "A language with the same name already exists");
        }
        return genreRepository.findById(genreId)
                .map(genreToUpdate -> genreRepository.save(genreToUpdate
                        .withName(genre.getName())))
                .orElseThrow(()->new ResourceNotFoundException(ENTITY,genreId));
    }

    @Override
    public ResponseEntity<?> delete(Long genreId) {
        return genreRepository.findById(genreId)
                .map(genre -> {
                    genreRepository.delete(genre);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY,genreId));
    }
}
