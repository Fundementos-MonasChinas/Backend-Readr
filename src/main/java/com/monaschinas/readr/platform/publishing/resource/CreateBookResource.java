package com.monaschinas.readr.platform.publishing.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookResource {
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
    private Date publishedAt;

    @NotNull
    @NotBlank
    private Long bookStatusId;

    @NotNull
    @NotBlank
    private Long sagaId;
}