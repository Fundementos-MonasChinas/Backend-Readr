package com.monaschinas.readr.platform.publishing.domain.persistence;

import com.monaschinas.readr.platform.publishing.domain.model.BookLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLanguageRepository extends JpaRepository<BookLanguage, Long> {
}
