package com.monaschinas.readr.platform.publishing.resource;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class BookLanguageResource {
    private Long id;
    private BookResource book;
    private LanguageResource language;
}
