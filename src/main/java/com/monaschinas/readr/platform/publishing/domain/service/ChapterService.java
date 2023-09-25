package com.monaschinas.readr.platform.publishing.domain.service;

import com.monaschinas.readr.platform.publishing.domain.model.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChapterService {
    List<Chapter> getAll();
    Page<Chapter> getAll(Pageable pageable);
    List<Chapter> getAllByBookId(Long bookId);
    Page<Chapter> getAllByBookId(Pageable pageable, Long bookId);
    Chapter getById(Long chapterId);
    Chapter create(Chapter chapter);
    Chapter update(Long chapterId, Chapter chapter);
    ResponseEntity<?> delete(Long chapterId);
}
