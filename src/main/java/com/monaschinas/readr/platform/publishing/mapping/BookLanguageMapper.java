package com.monaschinas.readr.platform.publishing.mapping;

import com.monaschinas.readr.platform.publishing.domain.model.Book;
import com.monaschinas.readr.platform.publishing.domain.model.BookLanguage;
import com.monaschinas.readr.platform.publishing.domain.model.Language;
import com.monaschinas.readr.platform.publishing.domain.service.BookService;
import com.monaschinas.readr.platform.publishing.domain.service.LanguageService;
import com.monaschinas.readr.platform.publishing.resource.BookLanguageResource;
import com.monaschinas.readr.platform.publishing.resource.CreateBookLanguageResource;
import com.monaschinas.readr.platform.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class BookLanguageMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    @Autowired
    private BookService bookService; // Asumiendo que tienes un servicio para Book

    @Autowired
    private LanguageService languageService; // Asumiendo que tienes un servicio para Language

    public BookLanguageResource toResource(BookLanguage model) {
        return mapper.map(model, BookLanguageResource.class);
    }

    public BookLanguage toModel(CreateBookLanguageResource resource) {
        BookLanguage bookLanguage = new BookLanguage();

        // Recupera las entidades completas basadas en los IDs
        Book book = bookService.getById(resource.getBookId());
        Language language = languageService.getById(resource.getLanguageId());

        // Establece las relaciones en la entidad BookLanguage
        bookLanguage.setBook(book);
        bookLanguage.setLanguage(language);

        return bookLanguage;
    }

    public Page<BookLanguageResource> modelListPage(List<BookLanguage> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, BookLanguageResource.class), pageable, modelList.size());
    }
}
