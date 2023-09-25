package com.monaschinas.readr.platform.publishing.domain.persistence;

import com.monaschinas.readr.platform.publishing.domain.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookStatusRepository extends JpaRepository<BookStatus, Long> {
    Optional<BookStatus> findByName(String name);
}
