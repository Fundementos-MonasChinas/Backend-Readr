package com.monaschinas.readr.platform.publishing.resource;

import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class LanguageResource {
    private Long id;
    private String name;
    private String abbreviation;
}
