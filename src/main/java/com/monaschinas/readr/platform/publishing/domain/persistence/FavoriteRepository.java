package com.monaschinas.readr.platform.publishing.domain.persistence;

import com.monaschinas.readr.platform.publishing.domain.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
