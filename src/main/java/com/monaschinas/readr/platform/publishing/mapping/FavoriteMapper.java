package com.monaschinas.readr.platform.publishing.mapping;

import com.monaschinas.readr.platform.publishing.domain.model.Book;
import com.monaschinas.readr.platform.publishing.domain.model.Favorite;
import com.monaschinas.readr.platform.publishing.domain.service.BookService;
import com.monaschinas.readr.platform.publishing.resource.CreateFavoriteResource;
import com.monaschinas.readr.platform.publishing.resource.FavoriteResource;
import com.monaschinas.readr.platform.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class FavoriteMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;
    @Autowired
    private BookService bookService;
    public FavoriteResource toResource(Favorite model) {
        return mapper.map(model, FavoriteResource.class);
    }

    public Favorite toModel(CreateFavoriteResource resource) {
        /*return mapper.map(resource, ProfileDevice.class);*/
        Favorite favorite = new Favorite();
        favorite.setProfileId(resource.getProfileId());
        Book book = bookService.getById(resource.getBookId());
        favorite.setBook(book);
        return favorite;
    }

    public Page<FavoriteResource> modelListPage(List<Favorite> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, FavoriteResource.class), pageable, modelList.size());
    }
}
