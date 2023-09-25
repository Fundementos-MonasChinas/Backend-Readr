package com.monaschinas.readr.platform.publishing.resource;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentResource {
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 128)
    private String content;

    @NotNull
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}
