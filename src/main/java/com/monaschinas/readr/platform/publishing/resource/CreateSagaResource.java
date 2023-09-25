package com.monaschinas.readr.platform.publishing.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateSagaResource {
    @NotNull
    @NotBlank
    @Size(max = 64)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 96)
    private String synopsis;

    @NotNull
    @NotBlank
    private Long sagaStatusId;
}
