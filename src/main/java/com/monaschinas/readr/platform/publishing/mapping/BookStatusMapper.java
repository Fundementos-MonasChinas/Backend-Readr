package com.monaschinas.readr.platform.publishing.mapping;

import com.monaschinas.readr.platform.publishing.domain.model.BookStatus;
import com.monaschinas.readr.platform.publishing.resource.BookStatusResource;
import com.monaschinas.readr.platform.publishing.resource.CreateBookStatusResource;
import com.monaschinas.readr.platform.publishing.resource.UpdateBookStatusResource;
import com.monaschinas.readr.platform.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BookStatusMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public BookStatusResource toResource(BookStatus model){ return mapper.map(model, BookStatusResource.class); }

    public Page<BookStatusResource> modelListPage(List<BookStatus> modelList, Pageable pageable){
        return new PageImpl<>(mapper.mapList(modelList, BookStatusResource.class), pageable, modelList.size());
    }

    public BookStatus toModel(CreateBookStatusResource resource){ return mapper.map(resource, BookStatus.class); }

    public BookStatus toModel(UpdateBookStatusResource resource){ return mapper.map(resource, BookStatus.class); }
}
