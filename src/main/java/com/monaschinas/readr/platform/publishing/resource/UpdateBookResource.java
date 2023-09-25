package com.monaschinas.readr.platform.publishing.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateBookResource {
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 64)
    private String title;

    @NotNull
    @NotBlank
    private Long authorId;

    @NotNull
    @NotBlank
    private String synopsis;

    @NotNull
    @NotBlank
    private Date publishedAt;

    @NotNull
    @NotBlank
    private Long languageId;

    @NotNull
    @NotBlank
    private Long bookStatusId;

    private Long sagaId;
}
