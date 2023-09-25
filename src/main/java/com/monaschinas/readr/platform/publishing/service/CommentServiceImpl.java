package com.monaschinas.readr.platform.publishing.service;

import com.monaschinas.readr.platform.publishing.domain.model.Comment;
import com.monaschinas.readr.platform.publishing.domain.persistence.CommentRepository;
import com.monaschinas.readr.platform.publishing.domain.service.CommentService;
import com.monaschinas.readr.platform.shared.exception.ResourceNotFoundException;
import com.monaschinas.readr.platform.shared.exception.ResourceValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service
public class CommentServiceImpl implements CommentService {
    private static final String ENTITY = "Comment";

    private final CommentRepository commentRepository;

    private final Validator validator;

    public CommentServiceImpl(CommentRepository commentRepository, Validator validator) {
        this.commentRepository = commentRepository;
        this.validator = validator;
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, commentId));
    }

    @Override
    public Comment create(Comment comment) {
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Long commentId, Comment comment) {
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return commentRepository.findById(commentId)
                .map(commentToUpdate -> commentRepository.save(commentToUpdate
                                .withContent(comment.getContent())
                                .withCreatedAt(comment.getCreatedAt())
                        )
                )
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, commentId));
    }

    @Override
    public ResponseEntity<?> delete(Long commentId) {
        return commentRepository.findById(commentId)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, commentId));
    }
}
