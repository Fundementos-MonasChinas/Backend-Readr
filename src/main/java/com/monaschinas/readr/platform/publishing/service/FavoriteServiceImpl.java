package com.monaschinas.readr.platform.publishing.service;

import com.monaschinas.readr.platform.publishing.domain.model.Favorite;
import com.monaschinas.readr.platform.publishing.domain.persistence.FavoriteRepository;
import com.monaschinas.readr.platform.publishing.domain.service.FavoriteService;
import com.monaschinas.readr.platform.shared.exception.ResourceNotFoundException;
import com.monaschinas.readr.platform.shared.exception.ResourceValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service
public class FavoriteServiceImpl implements FavoriteService {
    private static final String ENTITY = "Favorite";

    private final FavoriteRepository favoriteRepository;

    private final Validator validator;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, Validator validator) {
        this.favoriteRepository = favoriteRepository;
        this.validator = validator;
    }

    @Override
    public List<Favorite> getAll() {
        return favoriteRepository.findAll();
    }

    @Override
    public Favorite getById(Long favoriteId) {
        return favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, favoriteId));
    }

    @Override
    public Favorite create(Favorite favorite) {
        Set<ConstraintViolation<Favorite>> violations = validator.validate(favorite);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return favoriteRepository.save(favorite);
    }

    @Override
    public ResponseEntity<?> delete(Long favoriteId) {
        return favoriteRepository.findById(favoriteId)
                .map(favorite -> {
                    favoriteRepository.delete(favorite);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, favoriteId));
    }
}
