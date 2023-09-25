package com.monaschinas.readr.platform.profiling.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleResource {
  @NotNull
  @NotBlank
  @Size
  @Size(max = 24)
  private String name;
}
