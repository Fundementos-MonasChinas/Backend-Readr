package com.monaschinas.readr.platform.publishing.service;

import com.monaschinas.readr.platform.publishing.domain.model.Chapter;
import com.monaschinas.readr.platform.publishing.domain.model.Language;
import com.monaschinas.readr.platform.publishing.domain.persistence.ChapterRepository;
import com.monaschinas.readr.platform.publishing.domain.service.ChapterService;
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
public class ChapterServiceImpl implements ChapterService {
    private static final String ENTITY = "Chapter";

    private final ChapterRepository chapterRepository;

    private final Validator validator;

    public ChapterServiceImpl(ChapterRepository chapterRepository, Validator validator) {
        this.chapterRepository = chapterRepository;
        this.validator = validator;
    }

    @Override
    public List<Chapter> getAll() { return chapterRepository.findAll(); }

    @Override
    public Page<Chapter> getAll(Pageable pageable) { return chapterRepository.findAll(pageable); }

    @Override
    public List<Chapter> getAllByBookId(Long bookId) {
        return chapterRepository.findByBookId(bookId);
    }

    @Override
    public Page<Chapter> getAllByBookId(Pageable pageable, Long bookId) {
        return chapterRepository.findByBookId(pageable, bookId);
    }

    @Override
    public Chapter getById(Long chapterId) {
        return chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, chapterId));
    }

    @Override
    public Chapter create(Chapter chapter) {
        Set<ConstraintViolation<Chapter>> violations = validator.validate(chapter);
        if(!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }
        if(chapterRepository.findByTitle(chapter.getTitle()).isPresent()) {
            throw new ResourceValidationException(ENTITY, "A chapter with the same name already exists");
        }
        return chapterRepository.save(chapter);
    }

    @Override
    public Chapter update(Long chapterId, Chapter chapter) {
        Set<ConstraintViolation<Chapter>> violations = validator.validate(chapter);
        if(!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }
        Optional<Chapter> chapterWithTitle = chapterRepository.findByTitle(chapter.getTitle());

        if(chapterWithTitle.isPresent() && !chapterWithTitle.get().getId().equals(chapter.getId())) {
            throw new ResourceValidationException(ENTITY, "A chapter with the same name already exists");
        }
        return chapterRepository.findById(chapterId)
                .map(chapterToUpdate -> chapterRepository.save(chapterToUpdate
                        .withTitle(chapter.getTitle())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, chapterId));
    }

    @Override
    public ResponseEntity<?> delete(Long chapterId) {
        return chapterRepository.findById(chapterId)
                .map(chapter -> {
                    chapterRepository.delete(chapter);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, chapterId));
    }
}
