package com.monaschinas.readr.platform.publishing.domain.service;

import com.monaschinas.readr.platform.publishing.domain.model.BookLanguage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookLanguageService {
    List<BookLanguage> getAll();
    BookLanguage getById(Long bookLanguageId);
    BookLanguage create(BookLanguage bookLanguage);
    ResponseEntity<?> delete(Long bookLanguageId);
}
