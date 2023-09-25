package com.monaschinas.readr.platform.publishing.domain.persistence;

import com.monaschinas.readr.platform.publishing.domain.model.Saga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SagaRepository extends JpaRepository<Saga, Long> {
    Optional<Saga> findByTitle(String title);
}
