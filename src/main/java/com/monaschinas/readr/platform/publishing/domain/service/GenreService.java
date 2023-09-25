package com.monaschinas.readr.platform.publishing.domain.service;

import com.monaschinas.readr.platform.publishing.domain.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();
    Page<Genre> getAll(Pageable pageable);
    Genre getById(Long genreId);
    Genre create(Genre genre);
    Genre update(Long genreId, Genre genre);
    ResponseEntity<?> delete(Long genreId);
}
