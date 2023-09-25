package com.monaschinas.readr.platform.publishing.resource;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CommentResource {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long profileId;
    private BookResource book;
}
