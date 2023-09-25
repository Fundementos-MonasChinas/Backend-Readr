package com.monaschinas.readr.platform.publishing.resource;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class BookResource {
    private Long id;
    private String title;
    private Long authorId;
    private String synopsis;
    private Date publishedAt;
}
