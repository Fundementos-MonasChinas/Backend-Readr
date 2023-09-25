package com.monaschinas.readr.platform.publishing.domain.service;

import com.monaschinas.readr.platform.publishing.domain.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    List<Book> getAll();
    Page<Book> getAll(Pageable pageable);
    Book getById(Long bookId);
    Book create(Book book);
    Book update(Long bookId, Book book);
    ResponseEntity<?> delete(Long bookId);
}
