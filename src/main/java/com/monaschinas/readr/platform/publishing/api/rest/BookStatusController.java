package com.monaschinas.readr.platform.publishing.api.rest;

import com.monaschinas.readr.platform.publishing.domain.service.BookStatusService;
import com.monaschinas.readr.platform.publishing.mapping.BookStatusMapper;
import com.monaschinas.readr.platform.publishing.resource.BookStatusResource;
import com.monaschinas.readr.platform.publishing.resource.CreateBookStatusResource;
import com.monaschinas.readr.platform.publishing.resource.UpdateBookStatusResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/publishing/book-statuses", produces = "application/json")
public class BookStatusController {
  private final BookStatusService bookStatusService;

  private final BookStatusMapper mapper;

  public BookStatusController(BookStatusService bookStatusService, BookStatusMapper mapper){
    this.bookStatusService = bookStatusService;
    this.mapper = mapper;
  }

  @GetMapping
  public Page<BookStatusResource> getAllBookStatuses(Pageable pageable){
    return mapper.modelListPage(bookStatusService.getAll(), pageable);
  }
  @GetMapping({"bookStatusId"})
  public BookStatusResource getBookStatusById(@PathVariable Long bookStatusId){
    return mapper.toResource(bookStatusService.getById(bookStatusId));
  }

  @PostMapping
  public BookStatusResource createBookStatus(@RequestBody CreateBookStatusResource resource){
    return mapper.toResource(bookStatusService.create(mapper.toModel(resource)));
  }

  @PutMapping({"bookStatusId"})
  public BookStatusResource updateBookStatus(@PathVariable Long bookStatusId, @RequestBody UpdateBookStatusResource resource){
    return mapper.toResource(bookStatusService.update(bookStatusId, mapper.toModel(resource)));
  }

  @DeleteMapping({"bookStatusId"})
  public ResponseEntity<?> deleteBookStatus(@PathVariable Long bookStatusId){
    return bookStatusService.delete(bookStatusId);
  }
}
