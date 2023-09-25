package com.monaschinas.readr.platform.publishing.mapping;

import com.monaschinas.readr.platform.publishing.domain.model.Book;
import com.monaschinas.readr.platform.publishing.domain.model.Comment;
import com.monaschinas.readr.platform.publishing.domain.service.BookService;
import com.monaschinas.readr.platform.publishing.resource.CommentResource;
import com.monaschinas.readr.platform.publishing.resource.CreateCommentResource;
import com.monaschinas.readr.platform.publishing.resource.UpdateCommentResource;
import com.monaschinas.readr.platform.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class CommentMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    @Autowired
    private BookService bookService; // Suponiendo que tienes un servicio llamado BookService

    public CommentResource toResource(Comment model) {
        return mapper.map(model, CommentResource.class);
    }

    public Comment toModel(CreateCommentResource resource) {
        Comment comment = new Comment();

        // Mapear propiedades básicas
        comment.setContent(resource.getContent());
        comment.setCreatedAt(resource.getCreatedAt());
        comment.setProfileId(resource.getProfileId());

        // Usar el BookService para obtener el objeto Book completo basado en el ID
        Book book = bookService.getById(resource.getBookId());
        comment.setBook(book);

        return comment;
    }

    public Comment toModel(UpdateCommentResource resource) {
        return mapper.map(resource, Comment.class);
    }

    public Page<CommentResource> modelListPage(List<Comment> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, CommentResource.class), pageable, modelList.size());
    }
}
