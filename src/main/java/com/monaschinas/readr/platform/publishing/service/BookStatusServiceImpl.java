package com.monaschinas.readr.platform.publishing.service;

import com.monaschinas.readr.platform.publishing.domain.model.BookStatus;
import com.monaschinas.readr.platform.publishing.domain.persistence.BookStatusRepository;
import com.monaschinas.readr.platform.publishing.domain.service.BookStatusService;
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
public class BookStatusServiceImpl implements BookStatusService {
    private static final String ENTITY = "BookStatus";

    private final BookStatusRepository bookStatusRepository;

    private final Validator validator;

    public BookStatusServiceImpl(BookStatusRepository bookStatusRepository, Validator validator){
        this.bookStatusRepository = bookStatusRepository;
        this.validator = validator;
    }

    @Override
    public List<BookStatus> getAll() { return bookStatusRepository.findAll(); }

    @Override
    public Page<BookStatus> getAll(Pageable pageable){ return bookStatusRepository.findAll(pageable); }

    @Override
    public BookStatus getById(Long bookStatusId){
        return bookStatusRepository.findById(bookStatusId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, bookStatusId));
    }

    @Override
    public BookStatus create(BookStatus bookStatus){
        Set<ConstraintViolation<BookStatus>> violations = validator.validate(bookStatus);

        if (!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY, violations);
        }
        if (bookStatusRepository.findByName(bookStatus.getName()).isPresent()){
            throw new ResourceValidationException(ENTITY, "A book status with the same name already exists");
        }

        return bookStatusRepository.save(bookStatus);
    }

    @Override
    public BookStatus update(Long bookStatusId, BookStatus bookStatus){
        Set<ConstraintViolation<BookStatus>> violations = validator.validate(bookStatus);

        if(!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }
        Optional<BookStatus> bookStatusWithName = bookStatusRepository.findByName(bookStatus.getName());
        if (bookStatusWithName.isPresent() && !bookStatusWithName.get().getId().equals(bookStatus.getId())){
            throw new ResourceValidationException(ENTITY, "A book status with the same name already exists");
        }

        return bookStatusRepository.findById(bookStatusId)
                .map(bookToUpdate -> bookStatusRepository.save(bookToUpdate
                        .withName(bookStatus.getName())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, bookStatusId));
    }

    @Override
    public ResponseEntity<?> delete (Long bookStatusId){
        return bookStatusRepository.findById(bookStatusId)
                .map(bookStatus -> {
                    bookStatusRepository.delete(bookStatus);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, bookStatusId));
    }
}
