package com.monaschinas.readr.platform.profiling.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileResource {
  private Long id;

  @NotNull
  @NotBlank
  @Size(max = 32)
  private String firstName;

  @NotNull
  @NotBlank
  @Size(max = 32)
  private String lastName;
}
