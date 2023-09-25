package com.monaschinas.readr.platform.publishing.domain.service;

import com.monaschinas.readr.platform.publishing.domain.model.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookStatusService {
    List<BookStatus> getAll();
    Page<BookStatus> getAll(Pageable pageable);
    BookStatus getById(Long bookStatusId);
    BookStatus create(BookStatus bookStatus);
    BookStatus update(Long bookStatusId, BookStatus bookStatus);
    ResponseEntity<?> delete(Long bookStatusId);

}
