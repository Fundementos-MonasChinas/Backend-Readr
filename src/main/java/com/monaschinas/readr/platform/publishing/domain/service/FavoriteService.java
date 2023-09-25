package com.monaschinas.readr.platform.publishing.domain.service;

import com.monaschinas.readr.platform.publishing.domain.model.Favorite;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FavoriteService {
    List<Favorite> getAll();
    Favorite getById(Long favoriteId);
    Favorite create(Favorite favorite);
    ResponseEntity<?> delete(Long favoriteId);
}
