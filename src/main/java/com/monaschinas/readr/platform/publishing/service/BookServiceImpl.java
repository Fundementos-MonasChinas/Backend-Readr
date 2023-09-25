package com.monaschinas.readr.platform.publishing.service;

import com.monaschinas.readr.platform.publishing.domain.model.Book;
import com.monaschinas.readr.platform.publishing.domain.persistence.BookRepository;
import com.monaschinas.readr.platform.publishing.domain.service.BookService;
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
public class BookServiceImpl implements BookService {
    private static final String ENTITY = "Saga";
    private final BookRepository bookRepository;
    private final Validator validator;

    public BookServiceImpl (BookRepository bookRepository, Validator validator){
        this.bookRepository = bookRepository;
        this.validator = validator;
    }

    @Override
    public List<Book> getAll(){ return bookRepository.findAll(); }

    @Override
    public Page<Book> getAll(Pageable pageable){ return bookRepository.findAll(pageable); }

    @Override
    public Book getById(Long bookId){
        return bookRepository.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, bookId));
    }

    @Override
    public Book create(Book book){
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        if (!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }
        if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
            throw new ResourceValidationException(ENTITY, "A book with the same name already exists");
        }

        return bookRepository.save(book);
    }

    @Override
    public Book update(Long bookId, Book book){
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        if (!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }
        Optional<Book> bookWithTitle = bookRepository.findByTitle(book.getTitle());
        if (bookWithTitle.isPresent() && !bookWithTitle.get().getId().equals(book.getId())){
            throw new ResourceValidationException(ENTITY, "A book with the same name already exists");
        }

        return bookRepository.findById(bookId)
                .map(bookToUpdate -> bookRepository.save(bookToUpdate
                        .withTitle(book.getTitle())))
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, bookId));
    }

    @Override
    public ResponseEntity<?> delete(Long bookId){
        return bookRepository.findById(bookId)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, bookId));
    }
}
