package com.monaschinas.readr.platform.publishing.domain.service;

import com.monaschinas.readr.platform.publishing.domain.model.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    List<Comment> getAll();
    Comment getById(Long commentId);
    Comment create(Comment comment);
    Comment update(Long commentId, Comment comment);
    ResponseEntity<?> delete(Long commentId);
}
