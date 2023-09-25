package com.monaschinas.readr.platform.publishing.api.rest;

import com.monaschinas.readr.platform.publishing.domain.service.BookService;
import com.monaschinas.readr.platform.publishing.mapping.BookMapper;
import com.monaschinas.readr.platform.publishing.resource.BookResource;
import com.monaschinas.readr.platform.publishing.resource.CreateBookResource;
import com.monaschinas.readr.platform.publishing.resource.UpdateBookResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/publishing/books", produces = "application/json")
public class BookController {
  private final BookService bookService;
  private final BookMapper mapper;

  public BookController(BookService bookService, BookMapper mapper){
    this.bookService = bookService;
    this.mapper = mapper;
  }

  @GetMapping
  public Page<BookResource> getAllBooks(Pageable pageable){
    return mapper.modelListPage(bookService.getAll(), pageable);
  }

  @GetMapping({"bookId"})
  public BookResource getBookById(@PathVariable Long bookId)
  {
    return mapper.toResource(bookService.getById(bookId));
  }

  @PostMapping
  public BookResource createBook(@RequestBody CreateBookResource resource){
    return mapper.toResource(bookService.create(mapper.toModel(resource)));
  }

  @PutMapping("bookId")
  public BookResource updateBook(@PathVariable Long bookId, @RequestBody UpdateBookResource resource){
    return mapper.toResource(bookService.update(bookId, mapper.toModel(resource)));
  }

  @DeleteMapping({"bookId"})
  public ResponseEntity<?> deleteBook(@PathVariable Long bookId){
    return bookService.delete(bookId);
  }
}
