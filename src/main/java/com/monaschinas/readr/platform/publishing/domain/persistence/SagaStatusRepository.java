package com.monaschinas.readr.platform.publishing.domain.persistence;

import com.monaschinas.readr.platform.publishing.domain.model.SagaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SagaStatusRepository extends JpaRepository<SagaStatus, Long> {
    Optional<SagaStatus> findByName(String name);
}
