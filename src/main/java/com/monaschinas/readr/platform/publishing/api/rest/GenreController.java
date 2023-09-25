package com.monaschinas.readr.platform.publishing.api.rest;

import com.monaschinas.readr.platform.publishing.domain.service.GenreService;
import com.monaschinas.readr.platform.publishing.mapping.GenreMapper;
import com.monaschinas.readr.platform.publishing.resource.CreateGenreResource;
import com.monaschinas.readr.platform.publishing.resource.GenreResource;
import com.monaschinas.readr.platform.publishing.resource.UpdateGenreResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/publishing/genres", produces = "application/json")
public class GenreController {
  private final GenreService genreService;
  private final GenreMapper mapper;

  public GenreController(GenreService genreService, GenreMapper mapper) {
    this.genreService = genreService;
    this.mapper = mapper;
  }

  @GetMapping
  public Page<GenreResource> getAllGenres(Pageable pageable){
    return mapper.modelListPage(genreService.getAll(),pageable);
  }

  @GetMapping({"genreId"})
  public GenreResource getGenreById(@PathVariable Long genreId){
    return mapper.toResource(genreService.getById(genreId));
  }

  @PostMapping
  public GenreResource createGenre(@RequestBody CreateGenreResource resource){
    return mapper.toResource(genreService.create(mapper.toModel(resource)));
  }

  @PutMapping({"genreId"})
  public GenreResource updateGenre(@PathVariable Long genreId, @RequestBody UpdateGenreResource resource){
    return mapper.toResource(genreService.update(genreId, mapper.toModel(resource)));
  }

  @DeleteMapping({"genreId"})
  public ResponseEntity<?> deleteGenre(@PathVariable Long genreId){
    return genreService.delete(genreId);
  }
}
