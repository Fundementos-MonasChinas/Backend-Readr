package com.monaschinas.readr.platform.publishing.mapping;

import com.monaschinas.readr.platform.publishing.domain.model.Book;
import com.monaschinas.readr.platform.publishing.domain.model.BookStatus;
import com.monaschinas.readr.platform.publishing.domain.model.Saga;
import com.monaschinas.readr.platform.publishing.domain.service.BookStatusService;
import com.monaschinas.readr.platform.publishing.domain.service.SagaService;
import com.monaschinas.readr.platform.publishing.resource.BookResource;
import com.monaschinas.readr.platform.publishing.resource.CreateBookResource;
import com.monaschinas.readr.platform.publishing.resource.UpdateBookResource;
import com.monaschinas.readr.platform.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BookMapper {
    @Autowired
    EnhancedModelMapper mapper;

    @Autowired
    private SagaService sagaService;

    @Autowired
    private BookStatusService bookStatusService;

    public BookResource toResource(Book model){ return  mapper.map(model, BookResource.class); }

    public Book toModel(CreateBookResource resource){
        Book book = new Book();

        book.setAuthorId(resource.getAuthorId());
        book.setTitle(resource.getTitle());
        book.setSynopsis(resource.getSynopsis());
        book.setPublishedAt(resource.getPublishedAt());

        Saga saga = sagaService.getById(resource.getSagaId());
        book.setSaga(saga);

        BookStatus bookStatus = bookStatusService.getById(resource.getBookStatusId());
        book.setBookStatus(bookStatus);

        // Continúa mapeando cualquier otra propiedad si es necesario...

        return book;
    }

    public Book toModel(UpdateBookResource resource){ return mapper.map(resource, Book.class); }

    public Page<BookResource> modelListPage(List<Book> modelList, Pageable pageable){
        return new PageImpl<>(mapper.mapList(modelList, BookResource.class), pageable, modelList.size());
    }
}
