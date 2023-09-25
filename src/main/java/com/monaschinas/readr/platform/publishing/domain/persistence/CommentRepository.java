package com.monaschinas.readr.platform.publishing.domain.persistence;

import com.monaschinas.readr.platform.publishing.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
