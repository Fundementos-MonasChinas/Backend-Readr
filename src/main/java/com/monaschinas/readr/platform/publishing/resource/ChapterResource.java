package com.monaschinas.readr.platform.publishing.resource;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class ChapterResource {
    private Long id;
    private String title;
    private String document_content_url;
    private Long bookId;
}
