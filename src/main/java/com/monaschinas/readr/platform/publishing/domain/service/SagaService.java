package com.monaschinas.readr.platform.publishing.domain.service;

import com.monaschinas.readr.platform.publishing.domain.model.Saga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SagaService {
    List<Saga> getAll();
    Page<Saga> getAll(Pageable pageable);
    Saga getById(Long sagaId);
    Saga create(Saga saga);
    Saga update(Long sagaId, Saga saga);
    ResponseEntity<?> delete(Long sagaId);
}
