package com.monaschinas.readr.platform.publishing.resource;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentResource {
    @NotNull
    @NotBlank
    @Size(max = 128)
    private String content;

    @NotNull
    private Date createdAt;

    @NotNull
    private Long profileId;

    @NotNull
    private Long bookId;
}
