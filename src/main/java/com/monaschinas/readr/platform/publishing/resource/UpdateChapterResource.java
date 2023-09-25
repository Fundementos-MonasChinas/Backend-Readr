package com.monaschinas.readr.platform.publishing.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateChapterResource {
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 24)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 64)
    private String document_content_url;

    @NotNull
    @NotBlank
    private Long bookId;
}