package com.monaschinas.readr.platform.publishing.api.rest;

import com.monaschinas.readr.platform.publishing.domain.service.FavoriteService;
import com.monaschinas.readr.platform.publishing.mapping.FavoriteMapper;
import com.monaschinas.readr.platform.publishing.resource.CreateFavoriteResource;
import com.monaschinas.readr.platform.publishing.resource.FavoriteResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/api/v1/publishing/favorites", produces = "application/json")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final FavoriteMapper mapper;

    public FavoriteController(FavoriteService favoriteService, FavoriteMapper mapper) {
        this.favoriteService = favoriteService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<FavoriteResource> getAllFavorites(Pageable pageable) {
        return mapper.modelListPage(favoriteService.getAll(), pageable);
    }

    @GetMapping("{favoriteId}")
    public FavoriteResource getFavoriteById(@PathVariable Long favoriteId) {
        return mapper.toResource(favoriteService.getById(favoriteId));
    }

    @PostMapping
    public FavoriteResource createFavorite(@RequestBody CreateFavoriteResource resource) {
        return mapper.toResource(favoriteService.create(mapper.toModel(resource)));
    }

    @DeleteMapping("{favoriteId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long favoriteId) {
        return favoriteService.delete(favoriteId);
    }
}
