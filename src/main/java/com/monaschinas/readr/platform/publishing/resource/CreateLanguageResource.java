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
public class CreateLanguageResource {
    @NotNull
    @NotBlank
    @Size(max = 12)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 4)
    private String abbreviation;
}
